package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.datagen.i18.DatagenI18;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.data.PackOutput;


public final class ZhCnAttributes extends DatagenI18 {

  public ZhCnAttributes(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
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
  }
}
