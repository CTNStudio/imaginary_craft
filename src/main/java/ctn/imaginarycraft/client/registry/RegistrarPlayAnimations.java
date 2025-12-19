package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import team.unnamed.mocha.MochaEngine;

import java.util.function.Function;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarPlayAnimations {

  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      registerFactory(PlayerAnimUtil.STANDBY, 500);
      registerFactory(PlayerAnimUtil.ATTACK_STANDBY, 1000);
      registerFactory(PlayerAnimUtil.ATTACK, 1500);
    });
  }

  private static void registerFactory(ResourceLocation standby, int priority) {
    registerFactory(standby, priority, (controller, animationData, setter) ->
      PlayState.STOP);
  }

  private static void registerFactory(ResourceLocation standby, int priority,
                                      AnimationController.AnimationStateHandler animationHandler) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(standby, priority, player ->
      new PlayerAnimationController(player, animationHandler));
  }

  private static void registerFactory(ResourceLocation standby, int priority,
                                      AnimationController.AnimationStateHandler animationHandler,
                                      Function<AnimationController, MochaEngine<AnimationController>> molangRuntime) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(standby, priority, player ->
      new PlayerAnimationController(player, animationHandler, molangRuntime));
  }
}
