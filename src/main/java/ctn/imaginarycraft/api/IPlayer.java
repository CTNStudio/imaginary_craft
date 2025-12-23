package ctn.imaginarycraft.api;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Unique;

public interface IPlayer {
  static IPlayer of(Player player) {
    return (IPlayer) player;
  }

  int getImaginarycraft$AttackStrengthTicker();

  @Unique
  void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker);
}
