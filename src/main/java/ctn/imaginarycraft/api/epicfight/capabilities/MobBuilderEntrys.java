package ctn.imaginarycraft.api.epicfight.capabilities;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.ex_cap.modules.core.data.BuilderEntry;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public final class MobBuilderEntrys {
	/**
	 * 锤（大锤）
	 */
	public static final BuilderEntry HAMMER = new BuilderEntry(ImaginaryCraft.modRl("hammer"), WeaponCapability.builder());

	/**
	 * 梲（狼牙棒）
	 */
	public static final BuilderEntry MACE = new BuilderEntry(ImaginaryCraft.modRl("mace"), WeaponCapability.builder());

	/**
	 * 加农炮
	 */
	public static final BuilderEntry CANNON = new BuilderEntry(ImaginaryCraft.modRl("cannon"), WeaponCapability.builder());

	/**
	 * 枪
	 */
	public static final BuilderEntry GUN = new BuilderEntry(ImaginaryCraft.modRl("gun"), WeaponCapability.builder());

	/**
	 * 手枪
	 */
	public static final BuilderEntry PISTOL = new BuilderEntry(ImaginaryCraft.modRl("pistol"), WeaponCapability.builder());

	/**
	 * 来复枪
	 */
	public static final BuilderEntry RIFLE = new BuilderEntry(ImaginaryCraft.modRl("rifle"), WeaponCapability.builder());
}
