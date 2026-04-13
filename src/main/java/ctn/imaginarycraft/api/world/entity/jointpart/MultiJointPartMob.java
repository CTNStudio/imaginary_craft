package ctn.imaginarycraft.api.world.entity.jointpart;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.entity.ModEntityDataSerializers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class MultiJointPartMob<T extends LivingEntity & IJointPartEntity<?>> extends Mob implements IMultiJointPartEntity<T> {
	/**
	 * 关节部件ID数据的同步访问器
	 * <p>
	 * 用于在网络间同步关节部件的索引和UUID信息
	 */
	public static final EntityDataAccessor<Int2ObjectMap<Map.Entry<Integer, UUID>>> JOINT_PART_ID_DATA = SynchedEntityData.defineId(
		MultiJointPartMob.class, ModEntityDataSerializers.ENTITY_ID.get());

	/**
	 * 关节部件管理器，负责所有子部件实体的生命周期管理
	 */
	private final JointPartManager<T> jointPartManager;

	protected boolean isNewSpawn = true;

	protected MultiJointPartMob(EntityType<? extends Mob> entityType, Level level) {
		this(entityType, level, 0, false);
	}

	protected MultiJointPartMob(EntityType<? extends Mob> entityType, Level level, int maxCapacity, boolean shiftOnRemove) {
		this(entityType, level, new JointPartManager<>(maxCapacity, shiftOnRemove));
	}

	protected MultiJointPartMob(EntityType<? extends Mob> entityType, Level level, JointPartManager<T> manager) {
		super(entityType, level);
		this.jointPartManager = manager;
		this.entityData.set(JOINT_PART_ID_DATA, new Int2ObjectOpenHashMap<>());

		// 设置数据变更监听器，自动同步网络数据
		this.jointPartManager.setDataChangeListener(this::syncJointPartIdData);
	}

	/**
	 * 仅在服务端调用
	 * <p>
	 * 因为finalizeSpawn中生成时，id可能会错乱，所以必须推迟在onAddedToLevel中调用
	 */
	public void firstSpawn() {

	}

	/**
	 * 仅在服务端调用
	 * <p>
	 * 当实体从NBT数据加载后重新生成时调用，用于处理非首次生成的初始化逻辑
	 *
	 * @see #firstSpawn() 首次生成时的处理方法
	 */
	public void respawn() {

	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(JOINT_PART_ID_DATA, new Int2ObjectOpenHashMap<>());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
		if (key == JOINT_PART_ID_DATA && level().isClientSide) {
			jointPartManager.restoreFromSyncData(level(), entityData.get(JOINT_PART_ID_DATA));
		}
	}

	@Override
	public JointPartManager<T> getJointPartManager() {
		return jointPartManager;
	}

	/**
	 * 同步关节部件ID数据到网络
	 */
	private void syncJointPartIdData() {
		entityData.set(JOINT_PART_ID_DATA, jointPartManager.getSyncDataMap());
	}

	@Override
	public void onAddedToLevel() {
		if (!level().isClientSide) {
			if (isNewSpawn) {
				firstSpawn();
			} else {
				respawn();
			}
		}
		super.onAddedToLevel();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean(ImaginaryCraft.modRlText("IsNewSpawn"), false);
		jointPartManager.writeToNbt(compound);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains(ImaginaryCraft.modRlText("IsNewSpawn"))) {
			isNewSpawn = false;
		}

		ServerLevel serverLevel = (ServerLevel) level();
		jointPartManager.readFromNbt(compoundTag, serverLevel);
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);
		List<T> partsCopy = new ArrayList<>(jointPartManager.getJointParts());
		jointPartManager.clear();
		for (T jointPart : partsCopy) {
			if (jointPart != null) {
				jointPart.remove(reason);
			}
		}
	}

	public boolean addJointPart(@NotNull T jointPart) {
		boolean is = getJointPartManager().addJointPart(jointPart);
		if (is) {
			jointPart.setPos(position());
			level().addFreshEntity(jointPart);
		}
		return is;
	}
}
