package ctn.imaginarycraft.datagen.i18n;

import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueType;
import ctn.imaginarycraft.common.command.RationalityCommands;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.curio.EgoCurioItem;
import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.datagen.DatagenCuriosTest;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDamageTypes;
import ctn.imaginarycraft.init.item.ego.EgoCurioItems;
import ctn.imaginarycraft.init.tag.ModItemTags;
import ctn.imaginarycraft.linkage.jade.LivingEntityVulnerable;
import ctn.imaginarycraft.linkage.jade.ModPlugin;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;

public final class ZhCn extends DatagenI18n {
  public static final Map<DeferredItem<? extends Item>, String> ITEMS = new HashMap<>();
  public static final Map<String, String> MAP = new HashMap<>();

  public ZhCn(final PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add("pack.imaginarycraft.description", "异想工艺");
    addItemList(ITEMS);
    MAP.forEach(this::add);
    addJadePlugin(ModPlugin.ENTITY_LC_LEVEL, "生物等级");
    addJadePlugin(ModPlugin.BLOCK_LC_LEVEL, "方块等级");
    addJadePlugin(ModPlugin.ENTITY_LC_VULNERABLE, "生物易伤");
    add(LivingEntityVulnerable.ATTRIBUTE_DESCRIPTION_KEY, "易伤");
    add(LivingEntityVulnerable.PHYSICS_KEY, "物理易伤");
    add(LivingEntityVulnerable.SPIRIT_KEY, "精神易伤");
    add(LivingEntityVulnerable.EROSION_KEY, "侵蚀易伤");
    add(LivingEntityVulnerable.THE_SOUL_KEY, "灵魂易伤");

    //region 属性
    add(ModAttributes.PHYSICS_VULNERABLE.get(), "物理易伤");
    add(ModAttributes.SPIRIT_VULNERABLE.get(), "精神易伤");
    add(ModAttributes.EROSION_VULNERABLE.get(), "侵蚀易伤");
    add(ModAttributes.THE_SOUL_VULNERABLE.get(), "灵魂易伤");
    add(ModAttributes.MAX_RATIONALITY.get(), "最大理智值");
    add(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME.get(), "理智值自然恢复等待时间");
    add(ModAttributes.RATIONALITY_RECOVERY_AMOUNT.get(), "理智值自然恢复量");
    add(ModAttributes.FORTITUDE_POINTS.get(), "勇气点数");
    add(ModAttributes.PRUDENCE_POINTS.get(), "谨慎点数");
    add(ModAttributes.TEMPERANCE_POINTS.get(), "自律点数");
    add(ModAttributes.JUSTICE_POINTS.get(), "正义点数");
    add(ModAttributes.INTELLIGENCE_DEPARTMENT_ACTIVATION.get(), "情报部效果");
    //endregion

    //region 命令
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "已设置%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "已设置%s的最大理智基础值为：%d");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "已设置%s的基础理智值理智值自然恢复等待时间为：20*%d Tick");
    add(getFormattedKey(RationalityCommands.SET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "已设置%s的基础理智恢复为：每次%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "%s的最大理智值为：%d");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "%s的理智值理智值自然恢复等待时间为：20*%d Tick");
    add(getFormattedKey(RationalityCommands.GET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "%s的理智值自然恢复量为：每次%d点");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.VALUE.getName()), "已重置%s的理智值为：%d");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.MAX_VALUE.getName()), "已重置%s的最大理智基础值为：%d");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.NATURAL_RECOVERY_RATE.getName()), "已重置%s的基础理智值理智值自然恢复等待时间为：20*%dTick");
    add(getFormattedKey(RationalityCommands.RESET_KEY, RationalityCommands.ProcessType.RATIONALITY_RECOVERY_AMOUNT.getName()), "已重置%s的基础理智值自然恢复量为：每次%d点");
    add(getFormattedKey(RationalityCommands.RESET_KEY), "已重置%s的理智");
    //endregion

    //region 配置
    add(ModConfig.CLIENT.enableNewHealthBar, "是否开启新玩家生命条");
    add(ModConfig.CLIENT.enableFourColorDamageFilter, "是否开启玩家遭受四色伤害滤镜");
    add(ModConfig.CLIENT.enableLowRationalityFilter, "是否开启玩家低理智滤镜");
    add(ModConfig.SERVER.enableNaturalRationalityRationality, "是否开启自然恢复理智值");
    //endregion

    //region 饰品
    addCurios(DatagenCuriosTest.EGO_CURIOS_HEADWEAR, "头饰", "E.G.O饰品-头饰");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HEAD, "头部", "E.G.O饰品-头部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HINDBRAIN, "后脑", "E.G.O饰品-后脑");
    addCurios(DatagenCuriosTest.EGO_CURIOS_EYE, "眼部", "E.G.O饰品-眼部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_FACE, "面部", "E.G.O饰品-面部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_CHEEK, "脸颊", "E.G.O饰品-脸颊");
    addCurios(DatagenCuriosTest.EGO_CURIOS_MASK, "口罩", "E.G.O饰品-口罩");
    addCurios(DatagenCuriosTest.EGO_CURIOS_MOUTH, "口部", "E.G.O饰品-口部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_NECK, "颈部", "E.G.O饰品-颈部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_BROOCH, "胸针", "E.G.O饰品-胸针");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HAND, "手部", "E.G.O饰品-手部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_GLOVE, "手套", "E.G.O饰品-手套");
    addCurios(DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK, "右背", "E.G.O饰品-右背");
    addCurios(DatagenCuriosTest.EGO_CURIOS_LEFT_BACK, "右背", "E.G.O饰品-右背");
    EgoCurioItems.REGISTRY.getEntries().stream()
      .map(DeferredHolder::get)
      .filter(EgoCurioItem.class::isInstance)
      .map(EgoCurioItem.class::cast)
      .map(EgoCurioItem::getAndClearTooltipsI18nMap)
      .forEach(map -> map.forEach(this::add));
    //endregion

    //region 伤害类型
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
    //endregion

    //region 标签
    add(ModItemTags.EGO, "E.G.O");
    add(ModItemTags.EGO_ARMOUR, "E.G.O盔甲");
    add(ModItemTags.EGO_WEAPON, "E.G.O武器");
    add(ModItemTags.EGO_TOOL, "E.G.O工具");
    add(ModItemTags.EGO_CURIOS, "E.G.O饰品");
    add(ModItemTags.EGO_CURIOS_HEADWEAR, "E.G.O饰品-头饰");
    add(ModItemTags.EGO_CURIOS_HEAD, "E.G.O饰品-头部");
    add(ModItemTags.EGO_CURIOS_HINDBRAIN, "E.G.O饰品-后脑");
    add(ModItemTags.EGO_CURIOS_EYE, "E.G.O饰品-眼部");
    add(ModItemTags.EGO_CURIOS_FACE, "E.G.O饰品-面部");
    add(ModItemTags.EGO_CURIOS_CHEEK, "E.G.O饰品-脸颊");
    add(ModItemTags.EGO_CURIOS_MASK, "E.G.O饰品-口罩");
    add(ModItemTags.EGO_CURIOS_MOUTH, "E.G.O饰品-口部");
    add(ModItemTags.EGO_CURIOS_NECK, "E.G.O饰品-颈部");
    add(ModItemTags.EGO_CURIOS_BROOCH, "E.G.O饰品-胸针");
    add(ModItemTags.EGO_CURIOS_HAND, "E.G.O饰品-手部");
    add(ModItemTags.EGO_CURIOS_GLOVE, "E.G.O饰品-手套");
    add(ModItemTags.EGO_CURIOS_RIGHT_BACK, "E.G.O饰品-右背");
    add(ModItemTags.EGO_CURIOS_LEFT_BACK, "E.G.O饰品-左背");
    //endregion

    //region tooltip
    add(ItemVirtueUsageReq.USE_CONDITION, "E.G.O");
    add(ItemVirtueUsageReq.REQUIREMENT, "E.G.O");
    add(ItemVirtueUsageReq.INTERVAL, "E.G.O");
    add(ItemVirtueUsageReq.NOT_TO_EXCEED, "E.G.O");
    add(ItemVirtueUsageReq.NOT_LOWER_THAN, "E.G.O");
    add(VirtueType.FORTITUDE.getTooltipName(), "勇气");
    add(VirtueType.PRUDENCE.getTooltipName(), "谨慎");
    add(VirtueType.TEMPERANCE.getTooltipName(), "自律");
    add(VirtueType.JUSTICE.getTooltipName(), "正义");
    add(VirtueType.COMPOSITE.getTooltipName(), "综合");
    //endregion
  }
}
