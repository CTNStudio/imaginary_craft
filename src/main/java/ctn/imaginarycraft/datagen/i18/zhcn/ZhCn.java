package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.datagen.i18.DatagenI18;
import ctn.imaginarycraft.linkage.jade.LivingEntityVulnerable;
import ctn.imaginarycraft.linkage.jade.ModPlugin;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;

public final class ZhCn extends DatagenI18 {
  public static final Map<DeferredItem<? extends Item>, String> ITEM = new HashMap<>();

  public ZhCn(final PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add("pack.imaginarycraft.description", "异想工艺");
    addItemList(ITEM);
    addJadePlugin(ModPlugin.ENTITY_LC_LEVEL, "生物等级");
    addJadePlugin(ModPlugin.ENTITY_LC_VULNERABLE, "生物易伤");
    add(LivingEntityVulnerable.ATTRIBUTE_DESCRIPTION_KEY, "易伤");
    add(LivingEntityVulnerable.PHYSICS_KEY, "物理易伤");
    add(LivingEntityVulnerable.SPIRIT_KEY, "精神易伤");
    add(LivingEntityVulnerable.EROSION_KEY, "侵蚀易伤");
    add(LivingEntityVulnerable.THE_SOUL_KEY, "灵魂易伤");
  }
}
