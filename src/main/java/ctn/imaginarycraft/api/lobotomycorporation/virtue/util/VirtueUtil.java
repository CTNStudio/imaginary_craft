package ctn.imaginarycraft.api.lobotomycorporation.virtue.util;

import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueFortitude;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueJustice;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtuePrudence;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueTemperance;
import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

/**
 * 四德属性系统
 * <p>
 * 属性机制，包括勇气（Fortitude）、谨慎（Prudence）、自律（Temperance）和正义（Justice），
 * 每种属性都会影响玩家的不同能力。
 */
public final class VirtueUtil {
  /**
   * 获取综合评级
   * <p>
   * 计算并返回四个基本属性评级之和的综合评价。
   *
   * @return 综合评级
   */
  public static int getCompositeRatting(Player player) {
    int rating = getFortitudeRating(player) + getPrudenceRating(player) + getTemperanceRating(player) + getJusticeRating(player);
    if (rating >= 16) {
      return 5;
    }
    return Mth.clamp(rating / 3, 1, 4);
  }

  public static int getJusticeRating(final Player player) {
    return getJustice(player).getRatingPoints();
  }

  public static int getTemperanceRating(final Player player) {
    return getTemperance(player).getRatingPoints();
  }

  public static int getPrudenceRating(final Player player) {
    return getPrudence(player).getRatingPoints();
  }

  public static int getFortitudeRating(final Player player) {
    return getFortitude(player).getRatingPoints();
  }

  public static VirtueJustice getJustice(final Player player) {
    return player.getData(ModAttachments.JUSTICE);
  }

  public static VirtueTemperance getTemperance(final Player player) {
    return player.getData(ModAttachments.TEMPERANCE);
  }

  public static VirtuePrudence getPrudence(final Player player) {
    return player.getData(ModAttachments.PRUDENCE);
  }

  public static VirtueFortitude getFortitude(final Player player) {
    return player.getData(ModAttachments.FORTITUDE);
  }
}
