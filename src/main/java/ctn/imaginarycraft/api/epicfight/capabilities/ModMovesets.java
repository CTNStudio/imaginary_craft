package ctn.imaginarycraft.api.epicfight.capabilities;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSetEntry;

public final class ModMovesets {
	/**
	 * 锤（大锤）
	 */
	public static final MoveSetEntry HAMMER = new MoveSetEntry(ImaginaryCraft.modRl("hammer"), MoveSet.builder());

	/**
	 * 梲（狼牙棒）
	 */
	public static final MoveSetEntry MACE = new MoveSetEntry(ImaginaryCraft.modRl("mace"), MoveSet.builder());

	/**
	 * 加农炮
	 */
	public static final MoveSetEntry CANNON = new MoveSetEntry(ImaginaryCraft.modRl("cannon"), MoveSet.builder());

	/**
	 * 枪
	 */
	public static final MoveSetEntry GUN = new MoveSetEntry(ImaginaryCraft.modRl("gun"), MoveSet.builder());

	/**
	 * 手枪
	 */
	public static final MoveSetEntry PISTOL = new MoveSetEntry(ImaginaryCraft.modRl("pistol"), MoveSet.builder());

	/**
	 * 来复枪
	 */
	public static final MoveSetEntry RIFLE = new MoveSetEntry(ImaginaryCraft.modRl("rifle"), MoveSet.builder());

}
