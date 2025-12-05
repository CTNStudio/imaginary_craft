package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.datagen.i18.DatagenI18;
import ctn.imaginarycraft.init.ModDamageTypes;
import net.minecraft.data.PackOutput;


public final class ZhCnDamageType extends DatagenI18 {

  public ZhCnDamageType(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    addPlayerDeathMessage(ModDamageTypes.PHYSICS, "%s死于%s的造成的物理伤害");
    addPlayerDeathMessage(ModDamageTypes.SPIRIT, "%s死于%s的造成的精神污染");
    addPlayerDeathMessage(ModDamageTypes.EROSION, "%s死于%s的造成的侵蚀伤害");
    addPlayerDeathMessage(ModDamageTypes.THE_SOUL, "%s死于%s的造成的灵魂伤害");
    addPlayerDeathMessage(ModDamageTypes.EGO, "%s死于%s的E.G.O");
    addDeathMessage(ModDamageTypes.PHYSICS, "%s被剁成肉沫了");
    addDeathMessage(ModDamageTypes.SPIRIT, "%s精神崩溃而死");
    addDeathMessage(ModDamageTypes.EROSION, "%s因腐蚀而亡");
    addDeathMessage(ModDamageTypes.THE_SOUL, "%s的灵魂被超度了");
    addDeathMessage(ModDamageTypes.EGO, "%s死于E.G.O");
  }
}
