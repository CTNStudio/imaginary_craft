package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.player.*;

public interface IPlayer {
  static IPlayer of(Player player) {
    return player;
  }
}
