package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.player.Player;

public interface IPlayer {
	static IPlayer of(Player obj) {
		return obj;
  }
}
