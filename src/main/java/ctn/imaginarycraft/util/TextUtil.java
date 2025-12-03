package ctn.imaginarycraft.util;

import org.jetbrains.annotations.NotNull;

public final class TextUtil {

  /**
   * 格式化数字，添加千位分隔符
   *
   * @param number 要格式化的数字
   * @return 格式化后的字符串
   */
  public static String formatNumber(double number) {
    return formatNumber(number, 2);
  }

  /**
   * 格式化数字，添加千位分隔符
   *
   * @param number        要格式化的数字
   * @param decimalPlaces 小数位数
   * @return 格式化后的字符串
   */
  public static String formatNumber(double number, int decimalPlaces) {
    // 先格式化小数位数
    String formatted = String.format("%." + decimalPlaces + "f", number);

    // 分离整数部分和小数部分
    int dotIndex = formatted.indexOf('.');
    String integerPart = dotIndex >= 0 ? formatted.substring(0, dotIndex) : formatted;
    String decimalPart = dotIndex >= 0 ? formatted.substring(dotIndex) : "";

    // 添加千位分隔符
    return getResult(integerPart, decimalPart).toString();
  }

  private static @NotNull StringBuilder getResult(final String integerPart, final String decimalPart) {
    StringBuilder result = new StringBuilder();
    int len = integerPart.length();
    boolean isNegative = integerPart.startsWith("-");
    int startIndex = isNegative ? 1 : 0;

    // 添加符号
    if (isNegative) {
      result.append('-');
    }

    // 处理整数部分，每三位添加一个逗号
    for (int i = startIndex; i < len; i++) {
      // 如果不是第一位且从右往左数是3的倍数，则添加逗号
      if (i > startIndex && (len - i) % 3 == 0) {
        result.append(',');
      }
      result.append(integerPart.charAt(i));
    }

    // 添加小数部分
    result.append(decimalPart);
    return result;
  }
}
