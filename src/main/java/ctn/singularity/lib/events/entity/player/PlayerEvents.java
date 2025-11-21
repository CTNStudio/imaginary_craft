package ctn.singularity.lib.events.entity.player;

import ctn.singularity.lib.api.lobotomycorporation.util.RationalityUtil;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.eventexecute.RationalityEventExecutes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 玩家相关事件
 */
@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class PlayerEvents {
	/**
	 * 玩家重生或维度切换后
	 */
	@SubscribeEvent
	public static void reset(PlayerEvent.Clone event) {
		Player player = event.getEntity();
    Level level = player.level();

    if (event.isWasDeath()) {
      if (level instanceof ServerLevel serverLevel) {
        RationalityUtil.setRationalityValue(player, 0, false);
        RationalityUtil.setRationalityRecoveryTick(player, 0);
      }
    }
  }

	@SubscribeEvent
	public static void tick(PlayerTickEvent.Pre event) {
		Player player = event.getEntity();

		if (player instanceof ServerPlayer serverPlayer) {
      RationalityEventExecutes.refreshRationalityValue(serverPlayer);
		}
	}
}
