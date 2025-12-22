package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.api.client.ModPlayerAnimationController;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarPlayAnimations {

  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(PlayerAnimUtil.STANDBY, 600, player ->
        new ModPlayerAnimationController(player, (controller, animationData, setter) -> {
          if (!(controller instanceof ModPlayerAnimationController modPlayerAnimationController)) {
            return;
          }
          ItemStack mainHandItem = player.getMainHandItem();
          PlayerAnimationController headRotationController = PlayerAnimUtil.getPlayerAnimationController(player, PlayerAnimUtil.HEAD_ROTATION);
          if (mainHandItem.isEmpty() || !is(mainHandItem, EgoWeaponItems.MAGIC_BULLET.asItem())) {
            if (headRotationController != null) {
              headRotationController.stopTriggeredAnimation();
            }
            modPlayerAnimationController.stopTriggeredAnimation();
            return;
          }

          if (headRotationController != null) {
            headRotationController.triggerAnimation(PlayerAnimUtil.PLAYER_HEAD_ROTATION);
          }

          MagicBulletWeaponItem.ANIM_COLLECTION.get().executeAnim(mainHandItem, modPlayerAnimationController, animationData, setter);
        }, (controller, animationData, setter) -> PlayState.STOP));
      registerFactory(PlayerAnimUtil.HEAD_ROTATION, 700);
      registerFactory(PlayerAnimUtil.WALK, 1000);
      registerFactory(PlayerAnimUtil.ATTACK_STANDBY, 1500);
      registerFactory(PlayerAnimUtil.ATTACK, 2000);
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

  private static boolean is(ItemStack item, Item... items) {
    return Arrays.stream(items).anyMatch(item::is);
  }
}
