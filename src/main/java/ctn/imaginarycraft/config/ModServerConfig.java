package ctn.imaginarycraft.config;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public final class ModServerConfig extends ConfigUtil {

  //理智/精神伤害配置
  /**
   * 自然恢复理智值
   */
  public final BooleanValue enableNaturalRationalityRationality;

  ModServerConfig(Builder builder) {
    enableNaturalRationalityRationality = define(
      builder, true, "enable_natural_rationality_rationality", "自然恢复理智值");
  }
}
