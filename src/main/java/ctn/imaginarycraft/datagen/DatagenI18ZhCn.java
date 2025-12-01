package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.config.ConfigUtil;
import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import ctn.imaginarycraft.linkage.jade.LivingEntityVulnerable;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;


public final class DatagenI18ZhCn extends LanguageProvider {
  public DatagenI18ZhCn(PackOutput output) {
    super(output, ImaginaryCraft.ID, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add("pack.imaginarycraft.description", "异想工艺");
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
    addAttribute(ModAttributes.PHYSICS_VULNERABLE.get(), "物理易伤");
    addAttribute(ModAttributes.SPIRIT_VULNERABLE.get(), "精神易伤");
    addAttribute(ModAttributes.EROSION_VULNERABLE.get(), "侵蚀易伤");
    addAttribute(ModAttributes.THE_SOUL_VULNERABLE.get(), "灵魂易伤");
    add("config.jade.plugin_imaginarycraft.level", "等级");
    add("config.jade.plugin_imaginarycraft.vulnerable", "易伤");
    add(LivingEntityVulnerable.ATTRIBUTE_DESCRIPTION_KEY, "易伤");
    add(LivingEntityVulnerable.PHYSICS_KEY, "物理易伤");
    add(LivingEntityVulnerable.SPIRIT_KEY, "精神易伤");
    add(LivingEntityVulnerable.EROSION_KEY, "侵蚀易伤");
    add(LivingEntityVulnerable.THE_SOUL_KEY, "灵魂易伤");
    add(ModConfig.CLIENT.enableNewHealthBar, "是否开启新玩家生命条");
    add(ModConfig.CLIENT.enableFourColorDamageFilter, "是否开启玩家遭受四色伤害滤镜");
    add(ModConfig.CLIENT.enableLowRationalityFilter, "是否开启玩家低理智滤镜");
    add(ModConfig.SERVER.enableNaturalRationalityRationality, "是否开启自然恢复理智值");
  }

  private void add(ModConfigSpec.ConfigValue<?> configValue, String value) {
    add(ConfigUtil.getTranslation(configValue.getPath().toArray(String[]::new)), value);
  }

  private void add(ModConfigSpec.ConfigValue<?> configValue, String value, String tooltipValue) {
    add(configValue, value);
    add(ConfigUtil.getTranslation(configValue.getPath().toArray(String[]::new)) + ".tooltip", value);
  }

  public <T> void addDataComponent(DataComponentType<T> dataComponentType, String name) {
    add(dataComponentType.toString(), name);
  }

  /**
   * 生物属性翻译
   */
  public void addAttribute(Attribute attributeHolder, String name) {
    add(attributeHolder.getDescriptionId(), name);
  }

  /**
   * 死亡消息翻译
   */
  public void addDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath(), name);
  }

  /**
   * 玩家死亡消息翻译
   */
  public void addPlayerDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath() + ".player", name);
  }
}
