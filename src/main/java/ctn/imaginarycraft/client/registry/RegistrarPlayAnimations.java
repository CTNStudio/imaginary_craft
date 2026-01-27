package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.layered.IAnimation;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.animation.player.controller.AnimationControllerRegistry;
import ctn.imaginarycraft.client.animation.player.controller.ModPlayerAnimationController;
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
  public static final ResourceLocation PLAYER_HEAD_ROTATION = ImaginaryCraft.modRl("player.head_rotation");

  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> register());
  }

  private static void register() {
    AnimationControllerRegistry.initializeControllers();
    modRegisterFactory(PlayerAnimationUtil.STANDBY_OR_WALK, 600, (controller, data, animationSetter) -> controller instanceof ModPlayerAnimationController animationController ?
      AnimationControllerRegistry.STANDBY_OR_WALK.execute(animationController, data, animationSetter) : PlayState.STOP, true);
    modRegisterFactory(PlayerAnimationUtil.NORMAL_STATE, 1000, true);
    modRegisterFactory(PlayerAnimationUtil.WEAPON_STATE, 1500, true);
    modRegisterFactory(PlayerAnimationUtil.LEFT_HAND, 2000, true);
    modRegisterFactory(PlayerAnimationUtil.RIGHT_HAND, 2000, true);
    modRegisterFactory(PlayerAnimationUtil.HEAD_ROTATION, 3000,
      (controller, animationData, setter) -> {
        if (!(controller instanceof ModPlayerAnimationController playerAnimationController)) {
          return PlayState.STOP;
        }
        AbstractClientPlayer player = playerAnimationController.getPlayer();
        if (triggerHeadRotationAnimation(player, PlayerAnimationUtil.STANDBY_OR_WALK) ||
          triggerHeadRotationAnimation(player, PlayerAnimationUtil.NORMAL_STATE)) {
          return PlayState.CONTINUE;
        }
        return PlayState.STOP;
      }, false);
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority, boolean isFirstPerson) {
    modRegisterFactory(controllerId, priority, (controller, animationData, setter) -> PlayState.STOP, isFirstPerson);
  }

  private static void modRegisterFactory(ResourceLocation controllerId, int priority,
                                         AnimationController.AnimationStateHandler animationHandler, boolean isFirstPerson) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(controllerId, priority, player -> {
      ModPlayerAnimationController controller = new ModPlayerAnimationController(player, animationHandler);
      if (isFirstPerson) {
        controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
        controller.setFirstPersonConfiguration(PlayerAnimationUtil.DEFAULT_FIRST_PERSON_CONFIG);
      }
      return controller;
    });
  }

  private static boolean triggerHeadRotationAnimation(@NotNull AbstractClientPlayer player, @NotNull ResourceLocation id) {
    PlayerAnimationController headRotationController = PlayerAnimationUtil.getPlayerAnimationController(player, id);
    if (headRotationController != null && PlayerAnimationUtil.isExecutableAnimation(headRotationController, id)) {
      headRotationController.triggerAnimation(id);
      return true;
    }
    return false;
  }

  private static void registerFactory(ResourceLocation controllerId, int priority, boolean isFirstPerson) {
    registerFactory(controllerId, priority,
      (controller, animationData, setter) -> PlayState.STOP,
      isFirstPerson);
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

  private static void triggerHeadRotationAnimation(PlayerAnimationController headRotationController) {
    if (headRotationController != null && PlayerAnimationUtil.isExecutableAnimation(headRotationController, PLAYER_HEAD_ROTATION)) {
      headRotationController.triggerAnimation(PLAYER_HEAD_ROTATION);
    }
  }
}
