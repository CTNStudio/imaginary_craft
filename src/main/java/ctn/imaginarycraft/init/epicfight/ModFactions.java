package ctn.imaginarycraft.init.epicfight;

import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.client.gui.HealthBar;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public enum ModFactions implements Faction {
  /**
   * 考验-紫罗兰
   */
  ORDEALS_VIOLET(null, 0, 0x7f0cffff),
  /**
   * 考验-琥珀色
   */
  ORDEALS_AMBER(null, 0, 0xff7813ff),
  /**
   * 考验-绿色
   */
  ORDEALS_GREEN(null, 0, 0x27ff00ff),
  /**
   * 考验-血色
   */
  ORDEALS_CRIMSON(null, 0, 0xff0017ff),
  /**
   * 清道夫
   */
  THE_SWEEPERS(null, 0, 0x0e74ffff),
  /**
   * 首脑
   */
  ARBITER(null, 0, 0xffcc4bff),
  /**
   * 爪牙
   */
  CLAW(null, 0, 0x000000ff),
  ;

  private final ResourceLocation healthBar;
  private final int healthBarIndex;
  private final int damageColor;
  private final int id;

  ModFactions(ResourceLocation healthBar, int healthBarIndex, int damageColor) {
    this.healthBar = healthBar;
    this.healthBarIndex = healthBarIndex;
    this.damageColor = damageColor;
    this.id = Faction.ENUM_MANAGER.assign(this);
  }

  @Override
  public ResourceLocation healthBarTexture() {
    return healthBar == null ? HealthBar.HEALTHBARS1 : healthBar;
  }

  @Override
  public int healthBarIndex() {
    return healthBar == null ? 0 : healthBarIndex;
  }

  @Override
  public int damageColor() {
    return damageColor;
  }

  @Override
  public int universalOrdinal() {
    return id;
	}
}
