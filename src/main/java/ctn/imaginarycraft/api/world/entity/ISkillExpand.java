package ctn.imaginarycraft.api.world.entity;

import net.minecraft.nbt.CompoundTag;

public interface ISkillExpand {

	void readData(CompoundTag compound);

	void addData(CompoundTag compound);

	void tick();
}
