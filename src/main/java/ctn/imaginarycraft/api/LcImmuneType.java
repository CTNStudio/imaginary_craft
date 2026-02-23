package ctn.imaginarycraft.api;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * 免疫类型
 */
public enum LcImmuneType implements StringRepresentable {
  /**
   * 免疫
   */
  IMMUNITY(0, "immunity"),
  /**
   * 吸收
   */
  ABSORB(1, "absorb"),
  /**
   * 无效
   */
  INVALID(2, "invalid"),
  ;

  private final int index;
  private final String name;

  LcImmuneType(int index, String name) {
    this.index = index;
    this.name = name;
  }

  @Override
  public @NotNull String getSerializedName() {
    return name;
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }
}
