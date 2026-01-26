package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import net.minecraft.world.entity.player.Player;

public interface IPlayer {
  static IPlayer of(Player player) {
    return (IPlayer) player;
  }

  PlayerKeyClickUtil.ClickState imaginarycraft$getClickState(String keyName);

  void imaginarycraft$setClickState(String keyName, PlayerKeyClickUtil.ClickState clickState, boolean isSync);

  int imaginarycraft$getAttackSegmentCount();

  void imaginarycraft$setAttackSegmentCount(int attackSegmentCount);

  int getImaginarycraft$resetTimeForAttackSegmentCount();

  void setImaginarycraft$resetTimeForAttackSegmentCount(int imaginarycraft$resetTimeForAttackSegmentCount);
}
