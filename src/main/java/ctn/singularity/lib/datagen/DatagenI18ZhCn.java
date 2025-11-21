package ctn.singularity.lib.datagen;

import ctn.singularity.lib.core.LibMain;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;


public class DatagenI18ZhCn extends LanguageProvider {
  public DatagenI18ZhCn(PackOutput output) {
    super(output, LibMain.LIB_ID, "zh_cn");
  }

  @Override
  protected void addTranslations() {
  }
}
