package ctn.imaginarycraft.api.lobotomycorporation.virtue;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 四德评级
 */
public enum VirtueRating implements StringRepresentable {
  I("one", 1, 1),
  II("two", 2, 30),
  III("three", 3, 45),
  IV("four", 4, 65),
  V("five", 5, 85),
  EX("maxed", 6, 101),
  ;
  public static final List<VirtueRating> REVERSE_LIST;

  static {
    var values = new LinkedList<>(List.of(VirtueRating.values()));
    Collections.reverse(values);
    REVERSE_LIST = Collections.unmodifiableList(values);
  }

  private final String name;
  private final int value;
  private final int minValue;

  VirtueRating(String name, int value, int minValue) {
    this.name = name;
    this.value = value;
    this.minValue = minValue;
  }

  /**
   * 获取给定数值对应的评级
   *
   * @param value 数值
   * @return 对应的评级
   */
  public static VirtueRating getRating(int value) {
    for (VirtueRating virtueRating : REVERSE_LIST) {
      if (value < virtueRating.minValue) {
        continue;
      }
      return virtueRating;
    }
    return I;
  }

  /**
   * 获取给定数值对应的评级值
   *
   * @param value 数值
   * @return 对应的评级值
   */
  public static int getRatingValue(int value) {
    return getRating(value).getValue();
  }

  public int getValue() {
    return value;
  }

  public int getMinValue() {
    return minValue;
  }

  public String getName() {
    return name;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getSerializedName() {
    return ImaginaryCraft.ID + "." + getName();
  }
}
