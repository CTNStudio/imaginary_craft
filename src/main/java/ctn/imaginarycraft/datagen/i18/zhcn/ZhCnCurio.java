package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.datagen.DatagenCuriosTest;
import ctn.imaginarycraft.datagen.i18.DatagenI18;
import net.minecraft.data.PackOutput;


public final class ZhCnCurio extends DatagenI18 {

  public ZhCnCurio(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    addCurios(DatagenCuriosTest.EGO_CURIOS_HEADWEAR, "头饰", "E.G.O饰品-头饰");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HEAD, "头部", "E.G.O饰品-头部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HINDBRAIN, "后脑", "E.G.O饰品-后脑");
    addCurios(DatagenCuriosTest.EGO_CURIOS_EYE_AREA, "眼部", "E.G.O饰品-眼部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_FACE, "面部", "E.G.O饰品-面部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_CHEEK, "脸颊", "E.G.O饰品-脸颊");
    addCurios(DatagenCuriosTest.EGO_CURIOS_MASK, "口罩", "E.G.O饰品-口罩");
    addCurios(DatagenCuriosTest.EGO_CURIOS_MOUTH, "口部", "E.G.O饰品-口部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_NECK, "颈部", "E.G.O饰品-颈部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_CHEST, "胸部", "E.G.O饰品-胸部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_HAND, "手部", "E.G.O饰品-手部");
    addCurios(DatagenCuriosTest.EGO_CURIOS_GLOVE, "手套", "E.G.O饰品-手套");
    addCurios(DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK, "右背", "E.G.O饰品-右背");
    addCurios(DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK, "右背", "E.G.O饰品-右背");
  }
}
