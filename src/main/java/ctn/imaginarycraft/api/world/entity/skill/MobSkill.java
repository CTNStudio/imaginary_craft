package ctn.imaginarycraft.api.world.entity.skill;

import net.minecraft.nbt.CompoundTag;

public abstract class MobSkill {
	private final String name;
	private final int defaultCd;
	private final int maxCd;
	private int cd;
	private boolean isTriggerCdEnd = false;

	public MobSkill(String name, int maxCd) {
		this.name = name;
		this.maxCd = maxCd;
		this.defaultCd = maxCd;
	}

	public String getName() {
		return name;
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public int getMaxCd() {
		return maxCd;
	}

	public int getDefaultCd() {
		return defaultCd;
	}

	/**
	 * 技能重置冷却
	 */
	public void resetCd() {
		this.cd = this.defaultCd;
	}

	/**
	 * 技能进入冷却
	 */
	public void enterCd() {
		this.cd = this.maxCd;
	}

	public void tick() {
		if (this.cd > 0) {
			this.cd--;
		} else if (!isTriggerCdEnd) {
			this.cdEnd();
			isTriggerCdEnd = true;
		}
	}

	/**
	 * 技能冷却结束
	 */
	public abstract void cdEnd();

	/**
	 * 技能释放
	 */
	public abstract void launch();

	/**
	 * 读取数据
	 */
	public void readData(CompoundTag compound) {
		this.cd = compound.contains("Cd") ? compound.getInt("Cd") : defaultCd;
	}

	/**
	 * 写入数据
	 */
	public void addData(CompoundTag compound) {
		compound.putInt("Cd", this.cd);
	}
}