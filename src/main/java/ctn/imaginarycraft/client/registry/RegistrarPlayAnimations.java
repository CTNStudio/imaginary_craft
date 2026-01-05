package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.layered.IAnimation;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.animation.player.ModPlayerAnimationController;
import ctn.imaginarycraft.client.animation.player.StandbyPlayerAnimationController;
import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarPlayAnimations {
  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      modRegisterFactory(PlayerAnimationUtil.STANDBY_OR_WALK, 600, StandbyPlayerAnimationController::new, true);
      modRegisterFactory(PlayerAnimationUtil.HEAD_ROTATION, 700, false);
      modRegisterFactory(PlayerAnimationUtil.NORMAL_STATE, 1000, true);
      // TODO 以后想法子让他攻击的时候面向玩家面向的方向
      modRegisterFactory(PlayerAnimationUtil.WEAPON_STATE, 1500, true);

      modRegisterFactory(PlayerAnimationUtil.FIRST_PERSON, 2000, true);
    });
  }

  private static void registerFactory(ResourceLocation controllerId, int priority, boolean isFirstPerson) {
    registerFactory(controllerId, priority, (controller, animationData, setter) ->
      PlayState.STOP, isFirstPerson);
  }

  private static void registerFactory(ResourceLocation controllerId, int priority,
                                      AnimationController.AnimationStateHandler animationHandler, boolean isFirstPerson) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, player -> {
      PlayerAnimationController controller = new PlayerAnimationController(player, animationHandler);
      if (isFirstPerson) {
        controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
        controller.setFirstPersonConfiguration(PlayerAnimationUtil.DEFAULT_FIRST_PERSON_CONFIG);
      }
      return controller;
    });
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority, boolean isFirstPerson) {
    modRegisterFactory(controllerId, priority, (controller, animationData, setter) -> {
      },
      (controller, animationData, setter) ->
        PlayState.STOP, isFirstPerson);
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority,
                                         ModPlayerAnimationController.TickAnimationStateHandler tickAnimationStateHandler,
                                         AnimationController.AnimationStateHandler animationHandler, boolean isFirstPerson) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, player -> {
      ModPlayerAnimationController controller = new ModPlayerAnimationController(player, tickAnimationStateHandler, animationHandler);
      if (isFirstPerson) {
        controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
        controller.setFirstPersonConfiguration(PlayerAnimationUtil.DEFAULT_FIRST_PERSON_CONFIG);
      }
      return controller;
    });
  }

  private static void modRegisterFactory(@Nullable ResourceLocation controllerId, int priority,
                                         @NotNull PlayerAnimationFactory controllerFactory, boolean isFirstPerson) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, (player) -> {
      IAnimation invoke = controllerFactory.invoke(player);
      if (isFirstPerson && invoke instanceof AnimationController controller) {
        controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
        controller.setFirstPersonConfiguration(PlayerAnimationUtil.DEFAULT_FIRST_PERSON_CONFIG);
      }
      return invoke;
    });
  }

  private static void modRegisterFactory(@Nullable ResourceLocation controllerId, int priority,
                                         BiFunction<AbstractClientPlayer, AnimationController.AnimationStateHandler, PlayerAnimationController> function,
                                         boolean isFirstPerson) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, (player) -> {
      PlayerAnimationController controller = function.apply(player, (controller1, state, animSetter) -> PlayState.STOP);
      if (isFirstPerson) {
        controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
        controller.setFirstPersonConfiguration(PlayerAnimationUtil.DEFAULT_FIRST_PERSON_CONFIG);
      }
      return controller;
    });
  }
}
