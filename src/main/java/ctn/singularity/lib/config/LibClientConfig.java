package ctn.singularity.lib.config;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public final class LibClientConfig extends ConfigUtil {
  /**
   * 玩家低理智滤镜
   */
  public final BooleanValue enableLowRationalityFilter;

  /**
   * 玩家遭受四色伤害滤镜
   */
  public final BooleanValue enableFourColorDamageFilter;

  public LibClientConfig(Builder builder) {
    enableLowRationalityFilter = define(
      builder, true, "enable_low_rationality_filter", "玩家低理智滤镜");
    enableFourColorDamageFilter = define(
      builder, true, "enable_lobotomy_corporation_damage_filter", "玩家遭受四色伤害滤镜");
  }
}
