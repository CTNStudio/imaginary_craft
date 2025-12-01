package ctn.imaginarycraft.api;

public interface ColourText {
  int getColourValue();

  String getColourText();

  String getColourName();

  /**
   * 将ARGB颜色值转换为16位格式 (RGBA 4444)
   *
   * @param argb 32位ARGB颜色值
   * @return 16位RGBA颜色值
   */
  static int convertTo16Bit(int argb) {
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;
    int g = (argb >> 8) & 0xFF;
    int b = argb & 0xFF;

    // 转换为16位格式 (RGBA 4444)
    int a16 = (a >> 4) & 0xF;
    int r16 = (r >> 4) & 0xF;
    int g16 = (g >> 4) & 0xF;
    int b16 = (b >> 4) & 0xF;

    return (r16 << 12) | (g16 << 8) | (b16 << 4) | a16;
  }

  /**
   * 解析颜色字符串并转换为16位格式
   *
   * @param colorString 颜色字符串 (#RRGGBB 或 #RRGGBBAA)
   * @return 16位RGBA颜色值
   */
  static int parseColorTo16Bit(String colorString) {
    if (!colorString.startsWith("#") || (colorString.length() != 7 && colorString.length() != 9)) {
      throw new IllegalArgumentException("Invalid color format, expected #RRGGBB or #RRGGBBAA");
    }

    try {
      String hex = colorString.substring(1);
      int r, g, b, a = 255; // 默认不透明

      if (hex.length() == 6) {
        // RGB格式
        r = Integer.parseInt(hex.substring(0, 2), 16);
        g = Integer.parseInt(hex.substring(2, 4), 16);
        b = Integer.parseInt(hex.substring(4, 6), 16);
      } else if (hex.length() == 8) {
        // RGBA格式
        r = Integer.parseInt(hex.substring(0, 2), 16);
        g = Integer.parseInt(hex.substring(2, 4), 16);
        b = Integer.parseInt(hex.substring(4, 6), 16);
        a = Integer.parseInt(hex.substring(6, 8), 16);
      } else {
        throw new IllegalArgumentException("Invalid hex color format: " + colorString);
      }

      // 转换为16位格式 (RGBA 4444)
      int r16 = (r >> 4) & 0xF;
      int g16 = (g >> 4) & 0xF;
      int b16 = (b >> 4) & 0xF;
      int a16 = (a >> 4) & 0xF;

      return (r16 << 12) | (g16 << 8) | (b16 << 4) | a16;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid hex color format: " + colorString);
    }
  }

  /**
   * 将16位RGBA颜色值转换回32位ARGB格式
   *
   * @param rgba16 16位RGBA颜色值
   * @return 32位ARGB颜色值
   */
  static int convertFrom16Bit(int rgba16) {
    int r = (rgba16 >> 12) & 0xF;
    int g = (rgba16 >> 8) & 0xF;
    int b = (rgba16 >> 4) & 0xF;
    int a = rgba16 & 0xF;

    // 扩展4位到8位
    r = (r << 4) | r;
    g = (g << 4) | g;
    b = (b << 4) | b;
    a = (a << 4) | a;

    return (a << 24) | (r << 16) | (g << 8) | b;
  }
}
