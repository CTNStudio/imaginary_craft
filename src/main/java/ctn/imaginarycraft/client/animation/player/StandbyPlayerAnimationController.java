package ctn.imaginarycraft.client.animation.player;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.api.client.ModPlayerAnimationController;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class StandbyPlayerAnimationController extends ModPlayerAnimationController {
  public static final ResourceLocation PLAYER_HEAD_ROTATION = ImaginaryCraft.modRl("player.head_rotation");

  // 有移动，待机的物品放这
  public static final Map<Supplier<? extends Item>, PlayerAnimUtil.AnimCollection> ITEM = Map.of(
    EgoWeaponItems.MAGIC_BULLET, MagicBulletWeaponItem.ANIM_COLLECTION
  );

  @SuppressWarnings("unchecked")
  private static final Supplier<? extends Item>[] ITEM_KEY_CACHE = ITEM.keySet().toArray(Supplier[]::new);

  public StandbyPlayerAnimationController(AbstractClientPlayer player) {
    super(player, StandbyPlayerAnimationController::tickAnimationStateHandler, StandbyPlayerAnimationController::animationStateHandler);
  }

  private static PlayState animationStateHandler(AnimationController controller, AnimationData animationData, AnimationSetter animationSetter) {
    return PlayState.STOP;
  }

  private static void tickAnimationStateHandler(AnimationController animationController, AnimationData animationData, AnimationSetter animationSetter) {
    if (!(animationController instanceof ModPlayerAnimationController controller)) {
      return;
    }
    AbstractClientPlayer player = controller.getPlayer();
    ItemStack mainHandItem = player.getMainHandItem();

    PlayerAnimationController headRotationController = PlayerAnimUtil.getPlayerAnimationController(player, PlayerAnimUtil.HEAD_ROTATION);

    if (!isExecutableAnimation(mainHandItem)) {
      if (headRotationController != null) {
        headRotationController.stopTriggeredAnimation();
      }
      controller.stopTriggeredAnimation();
      return;
    }

    // 触发头部旋转动画
    if (headRotationController != null) {
      headRotationController.triggerAnimation(PLAYER_HEAD_ROTATION);
    }

    // 触发物品动画
    MagicBulletWeaponItem.ANIM_COLLECTION.executeAnim(mainHandItem, controller, animationData, animationSetter);
  }

  /**
   * 是否能执行动画
   */
  private static boolean isExecutableAnimation(ItemStack item) {
    return !item.isEmpty() && Arrays.stream(ITEM_KEY_CACHE)
      .anyMatch(i -> i.get() == item.getItem());
  }
}
