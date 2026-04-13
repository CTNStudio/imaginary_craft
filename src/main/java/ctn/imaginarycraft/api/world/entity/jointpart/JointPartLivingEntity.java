package ctn.imaginarycraft.api.world.entity.jointpart;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.entity.ModEntityDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.Joint;

import java.util.*;

public abstract class JointPartLivingEntity<T extends LivingEntity & IMultiJointPartEntity<?>> extends LivingEntity implements IJointPartEntity<T> {
	protected static final EntityDataAccessor<String> PARENT_STRING_DATA = SynchedEntityData.defineId(JointPartLivingEntity.class, EntityDataSerializers.STRING);
	protected static final EntityDataAccessor<OptionalInt> PARENT_ID_DATA = SynchedEntityData.defineId(JointPartLivingEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
	protected static final EntityDataAccessor<List<String>> JOINT_NAMES_DATA = SynchedEntityData.defineId(JointPartLivingEntity.class, ModEntityDataSerializers.JOINT_NAMES.get());

	private final List<String> jointNames = new ArrayList<>();
	private String partName;
	private T parent;

	protected JointPartLivingEntity(EntityType<? extends LivingEntity> entityType, Level level) {
		this(entityType, level, null, "", new String[0]);
	}

	protected JointPartLivingEntity(EntityType<? extends LivingEntity> entityType, Level level, T parent, @NotNull String partName, String... jointNames) {
		super(entityType, level);
		setJointNames(jointNames);
		setPartName(partName);
		setParent(parent);
	}

	protected JointPartLivingEntity(EntityType<? extends LivingEntity> entityType, Level level, T parent, @NotNull String partName, Joint... joints) {
		this(entityType, level, parent, partName, Arrays.stream(joints).map(Joint::getName).toArray(String[]::new));
	}

	/**
	 * 初始化关节名称列表并同步到实体数据
	 *
	 * @param jointNames 关节名称数组
	 */
	public void setJointNames(String... jointNames) {
		this.jointNames.clear();
		if (jointNames.length > 0) {
			this.jointNames.addAll(Arrays.asList(jointNames));
		}
		entityData.set(JOINT_NAMES_DATA, new ArrayList<>(this.jointNames));
	}

	/**
	 * 从 Joint 对象设置关节名称
	 *
	 * @param joints 关节对象数组
	 */
	protected void setJointNames(Joint... joints) {
		setJointNames(Arrays.stream(joints).map(Joint::getName).toArray(String[]::new));
	}

	/**
	 * 设置父实体并同步数据
	 *
	 * @param parent 父实体
	 */
	public void setParent(T parent) {
		this.parent = parent;
		entityData.set(PARENT_ID_DATA, parent == null ? OptionalInt.empty() : OptionalInt.of(parent.getId()));
	}

	/**
	 * 设置部件名称并同步到实体数据
	 *
	 * @param partName 部件名称
	 */
	public void setPartName(@NotNull String partName) {
		this.partName = partName;
		entityData.set(PARENT_STRING_DATA, partName);
	}

	@Override
	public List<String> getJointsNames() {
		return Collections.unmodifiableList(jointNames);
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide && tickCount % 20 * 4 == 0) {
			if (parent == null || parent.isRemoved()) {
				discard();
			}
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PARENT_ID_DATA, OptionalInt.empty());
		builder.define(PARENT_STRING_DATA, "");
		builder.define(JOINT_NAMES_DATA, new ArrayList<>());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
		if (!this.level().isClientSide) {
			return;
		}

		if (key == PARENT_STRING_DATA) {
			partName = this.entityData.get(PARENT_STRING_DATA);
			return;
		}

		if (key == PARENT_ID_DATA) {
			OptionalInt optionalInt = this.entityData.get(PARENT_ID_DATA);
			if (optionalInt.isEmpty()) {
				parent = null;
				return;
			}
			parent = (T) level().getEntity(optionalInt.getAsInt());
			return;
		}

		if (key == JOINT_NAMES_DATA) {
			jointNames.clear();
			jointNames.addAll(this.entityData.get(JOINT_NAMES_DATA));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (partName != null) {
			compound.putString(ImaginaryCraft.modRlText("PartName"), partName);
		}

		if (parent != null) {
			compound.putUUID(ImaginaryCraft.modRlText("Parent"), parent.getUUID());
		}

		if (!jointNames.isEmpty()) {
			ListTag listTag = new ListTag();
			for (String jointPart : jointNames) {
				listTag.add(StringTag.valueOf(jointPart));
			}
			compound.put(ImaginaryCraft.modRlText("JointNames"), listTag);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains(ImaginaryCraft.modRlText("PartName"))) {
			setPartName(compound.getString(ImaginaryCraft.modRlText("PartName")));
		} else {
			setPartName("");
		}

		if (compound.hasUUID(ImaginaryCraft.modRlText("Parent"))) {
			setParent((T) ((ServerLevel) level()).getEntity(compound.getUUID(ImaginaryCraft.modRlText("Parent"))));
		} else {
			setParent(null);
		}

		if (compound.get(ImaginaryCraft.modRlText("JointNames")) instanceof ListTag list) {
			ArrayList<String> objects = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				objects.add(list.getString(i));
			}
			setJointNames(objects.toArray(String[]::new));
		} else {
			setJointNames(new String[0]);
		}
	}

	@Override
	public String getPartName() {
		return partName;
	}
}
