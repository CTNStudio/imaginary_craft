package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.datagen.i18.DatagenI18;
import ctn.imaginarycraft.init.tag.ModItemTags;
import net.minecraft.data.PackOutput;


public final class ZhCnTag extends DatagenI18 {

  public ZhCnTag(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add(ModItemTags.EGO, "E.G.O");
    add(ModItemTags.EGO_ARMOUR, "E.G.O盔甲");
    add(ModItemTags.EGO_WEAPON, "E.G.O武器");
    add(ModItemTags.EGO_TOOL, "E.G.O工具");
    add(ModItemTags.EGO_CURIOS_HEAD, "E.G.O饰品-头部");
    add(ModItemTags.EGO_CURIOS_HINDBRAIN, "E.G.O饰品-后脑");
    add(ModItemTags.EGO_CURIOS_EYE_AREA, "E.G.O饰品-眼部");
    add(ModItemTags.EGO_CURIOS_FACE, "E.G.O饰品-面部");
    add(ModItemTags.EGO_CURIOS_CHEEK, "E.G.O饰品-脸颊");
    add(ModItemTags.EGO_CURIOS_MASK, "E.G.O饰品-口罩");
    add(ModItemTags.EGO_CURIOS_MOUTH, "E.G.O饰品-口部");
    add(ModItemTags.EGO_CURIOS_NECK, "E.G.O饰品-颈部");
    add(ModItemTags.EGO_CURIOS_CHEST, "E.G.O饰品-胸部");
    add(ModItemTags.EGO_CURIOS_HAND, "E.G.O饰品-手部");
    add(ModItemTags.EGO_CURIOS_GLOVE, "E.G.O饰品-手套");
    add(ModItemTags.EGO_CURIOS_RIGHT_BACK, "E.G.O饰品-右背");
    add(ModItemTags.EGO_CURIOS_LEFT_BACK, "E.G.O饰品-左背");
  }
}
