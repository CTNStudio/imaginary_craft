package ctn.imaginarycraft.client.eventexecute;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.function.Supplier;

public final class PlayerEventAnimExecute {
  private static ItemStack mainHandItemStack = ItemStack.EMPTY;

  public static void tick(AbstractClientPlayer clientPlayer) {
    ItemStack newMainHandItem = clientPlayer.getMainHandItem();
    if (!ItemStack.isSameItem(newMainHandItem, mainHandItemStack)) {
      mainHandItemSimpleChanges(clientPlayer, newMainHandItem);
    }
    if (!ItemStack.isSameItemSameComponents(newMainHandItem, mainHandItemStack)) {
      mainHandItemStack = newMainHandItem;
    }
  }

  public static void mainHandItemSimpleChanges(AbstractClientPlayer clientPlayer, ItemStack mainHandItem) {
    PlayerAnimationController standbyController = PlayerAnimUtil.getPlayerAnimationController(clientPlayer, PlayerAnimUtil.STANDBY);
    if (standbyController == null) {
      return;
    }

    PlayerAnimationController headRotationController = PlayerAnimUtil.getPlayerAnimationController(clientPlayer, PlayerAnimUtil.HEAD_ROTATION);
    if (mainHandItem.isEmpty() || !is(mainHandItem, EgoWeaponItems.MAGIC_BULLET.asItem())) {
      if (headRotationController != null) {
        headRotationController.stopTriggeredAnimation();
      }
      standbyController.stopTriggeredAnimation();
      return;
    }

    if (headRotationController != null) {
      headRotationController.triggerAnimation(PlayerAnimUtil.PLAYER_HEAD_ROTATION);
    }

    // 按这样扩展
    if (PlayerAnimUtil.playItemAnimation(mainHandItem, standbyController, MagicBulletWeaponItem.STANDBY, EgoWeaponItems.MAGIC_BULLET.asItem())) {
    }
  }

  private static boolean is(ItemStack item, Item... items) {
    return Arrays.stream(items).anyMatch(item::is);
  }

  public record AnimCollection(ResourceLocation standby, ResourceLocation move,
                               Supplier<? extends Item>... items) {
    @SafeVarargs
    public AnimCollection {
    }

    public Boolean executeAnim(ItemStack toItemStack, PlayerAnimationController controller, AnimationData state, AnimationController.AnimationSetter animationSetter) {
      for (var item : items) {
        if (!toItemStack.is(item.get())) {
          continue;
        }
        if (state.isMoving()) {
          movingAnim(controller);
        } else {
          standbyAnim(controller);
        }
        return true;
      }
      return false;
    }

    public void movingAnim(PlayerAnimationController controller) {
      if (PlayerAnimUtil.isExecutableAnimation(controller, move)) {
        controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), move, true);
      }
    }

    public void standbyAnim(PlayerAnimationController controller) {
      if (PlayerAnimUtil.isExecutableAnimation(controller, standby)) {
        controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), standby, true);
      }
    }
  }
}
