package ctn.imaginarycraft.datagen.i18.zhcn;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.datagen.i18.DatagenI18;
import net.minecraft.data.PackOutput;


public final class ZhCnConfig extends DatagenI18 {

  public ZhCnConfig(PackOutput output) {
    super(output, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add(ModConfig.CLIENT.enableNewHealthBar, "是否开启新玩家生命条");
    add(ModConfig.CLIENT.enableFourColorDamageFilter, "是否开启玩家遭受四色伤害滤镜");
    add(ModConfig.CLIENT.enableLowRationalityFilter, "是否开启玩家低理智滤镜");
    add(ModConfig.SERVER.enableNaturalRationalityRationality, "是否开启自然恢复理智值");
  }
}
