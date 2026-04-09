package ctn.imaginarycraft.api.world.entity.jointpart;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.Joint;

import java.util.Optional;
import java.util.UUID;

public abstract class JointPartMobEntity<T extends Mob> extends Mob implements IJointPartEntity<T> {
	protected static final EntityDataAccessor<Optional<UUID>> PARENT_UUID_DATA = SynchedEntityData.defineId(JointPartMobEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	private Joint[] joints;
	private T parent;

	protected JointPartMobEntity(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
		entityData.set(PARENT_UUID_DATA, Optional.empty());
	}

	protected JointPartMobEntity(EntityType<? extends Mob> entityType, Level level, T parent, Joint... joints) {
		super(entityType, level);
		this.joints = joints;
		this.parent = parent;
		entityData.set(PARENT_UUID_DATA, Optional.of(parent.getUUID()));
	}

	@Override
	public Joint[] getJoints() {
		return joints;
	}

	protected void setJoints(Joint... joints) {
		this.joints = joints;
	}

	@Override
	public T getParent() {
		return parent;
	}

	protected void setParent(T parent) {
		this.parent = parent;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PARENT_UUID_DATA, Optional.empty());
	}
}
