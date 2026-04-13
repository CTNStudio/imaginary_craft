package ctn.imaginarycraft.api.epicfight.capabilities;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.ex_cap.modules.core.data.ExCapData;
import yesman.epicfight.api.ex_cap.modules.core.data.ExCapDataEntry;

public final class ModExCapDataSets {
	/**
	 * 锤（大锤）
	 */
	public static final ExCapDataEntry HAMMER = new ExCapDataEntry(ImaginaryCraft.modRl("hammer"), ExCapData.builder());

	/**
	 * 梲（狼牙棒）
	 */
	public static final ExCapDataEntry MACE = new ExCapDataEntry(ImaginaryCraft.modRl("mace"), ExCapData.builder());

	/**
	 * 加农炮
	 */
	public static final ExCapDataEntry CANNON = new ExCapDataEntry(ImaginaryCraft.modRl("cannon"), ExCapData.builder());

	/**
	 * 枪
	 */
	public static final ExCapDataEntry GUN = new ExCapDataEntry(ImaginaryCraft.modRl("gun"), ExCapData.builder());

	/**
	 * 手枪
	 */
	public static final ExCapDataEntry PISTOL = new ExCapDataEntry(ImaginaryCraft.modRl("pistol"), ExCapData.builder());

	/**
	 * 来复枪
	 */
	public static final ExCapDataEntry RIFLE = new ExCapDataEntry(ImaginaryCraft.modRl("rifle"), ExCapData.builder());
}
