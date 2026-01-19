package ctn.imaginarycraft.client.animation.player;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.MirrorModifier;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.remote.SolemnLamentWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import ctn.imaginarycraft.util.ItemUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class StandbyPlayerAnimationController extends ModPlayerAnimationController {
  public static final ResourceLocation PLAYER_HEAD_ROTATION = ImaginaryCraft.modRl("player.head_rotation");

  public StandbyPlayerAnimationController(AbstractClientPlayer player) {
    super(player, StandbyPlayerAnimationController::getTickAnimationStateHandler, StandbyPlayerAnimationController::getAnimationStateHandler);
  }

  private static void getTickAnimationStateHandler(AnimationController controller, AnimationData state, AnimationSetter animationSetter) {
    if (!(controller instanceof StandbyPlayerAnimationController animationController)) {
      return;
    }
    AbstractClientPlayer player = animationController.getPlayer();

    ItemStack mainHandItem = player.getMainHandItem();
    ItemStack offHandItem = player.getOffhandItem();

    animationController.removeModifierIf(MirrorModifier.class::isInstance);

    // 双手动画

    if (ItemUtil.anyMatchIs(mainHandItem, EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE) &&
      ItemUtil.anyMatchIs(offHandItem, EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE)) {
      SolemnLamentWeaponItem.TWIN_ANIM_COLLECTION.executeAnim(animationController, state, animationSetter);
      return;
    }

    //  主手动画

    if (mainHandItem.is(EgoWeaponItems.MAGIC_BULLET)) {
      MagicBulletWeaponItem.ANIM_COLLECTION.executeAnim(animationController, state, animationSetter);
      return;
    }

    if (ItemUtil.anyMatchIs(mainHandItem, EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE)) {
      SolemnLamentWeaponItem.ANIM_COLLECTION.executeAnim(animationController, state, animationSetter);
      return;
    }

    // 副手动画

    if (ItemUtil.anyMatchIs(offHandItem, EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE)) {
      animationController.addModifierLast(new MirrorModifier());
      SolemnLamentWeaponItem.ANIM_COLLECTION.executeAnim(animationController, state, animationSetter);
      return;
    }

    animationController.stopTriggeredAnimation();
  }

  private static PlayState getAnimationStateHandler(AnimationController controller, AnimationData animationData, AnimationSetter animationSetter) {

    return PlayState.STOP;
  }

  private static void triggerHeadRotationAnimation(PlayerAnimationController headRotationController) {
    if (headRotationController != null && PlayerAnimationUtil.isExecutableAnimation(headRotationController, PLAYER_HEAD_ROTATION)) {
      headRotationController.triggerAnimation(PLAYER_HEAD_ROTATION);
    }
  }
}
