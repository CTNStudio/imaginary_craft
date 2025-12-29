package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.animation.player.ModPlayerAnimationController;
import ctn.imaginarycraft.client.animation.player.StandbyPlayerAnimationController;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarPlayAnimations {

  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      modRegisterFactory(PlayerAnimUtil.STANDBY_OR_WALK, 600, StandbyPlayerAnimationController::new);
      modRegisterFactory(PlayerAnimUtil.HEAD_ROTATION, 700);
      modRegisterFactory(PlayerAnimUtil.NORMAL_STATE, 1000);
      modRegisterFactory(PlayerAnimUtil.WEAPON_STATE, 1500);
    });
  }

  private static void registerFactory(ResourceLocation controllerId, int priority) {
    registerFactory(controllerId, priority, (controller, animationData, setter) ->
      PlayState.STOP);
  }

  private static void modRegisterFactory(@Nullable ResourceLocation controllerId, int priority, @NotNull PlayerAnimationFactory factory) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, factory);
  }

  private static void registerFactory(ResourceLocation controllerId, int priority,
                                      AnimationController.AnimationStateHandler animationHandler) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, player ->
      new PlayerAnimationController(player, animationHandler));
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority) {
    modRegisterFactory(controllerId, priority, (controller, animationData, setter) -> {
      },
      (controller, animationData, setter) ->
        PlayState.STOP);
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority,
                                         ModPlayerAnimationController.TickAnimationStateHandler tickAnimationStateHandler,
                                         AnimationController.AnimationStateHandler animationHandler) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, player ->
      new ModPlayerAnimationController(player, tickAnimationStateHandler, animationHandler));
  }
}
