package ctn.imaginarycraft.client.animation.player.controller;

import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.remote.SolemnLamentWeaponItem;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;

import java.util.ArrayList;
import java.util.List;

public class AnimationControllerRegistry {
  public static final GenericAnimationController STANDBY_OR_WALK = new GenericAnimationController();

  public static void initializeControllers() {
    STANDBY_OR_WALK
      .hands(
        GenericAnimationController.itemCondition(EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE),
        SolemnLamentWeaponItem.TWIN_ANIM_COLLECTION::executeAnim)
      .mainHand(
        GenericAnimationController.itemCondition(EgoWeaponItems.MAGIC_BULLET),
        MagicBulletWeaponItem.ANIM_COLLECTION::executeAnim)
      .hand(
        GenericAnimationController.itemCondition(EgoWeaponItems.SOLEMN_LAMENT_BLACK, EgoWeaponItems.SOLEMN_LAMENT_WHITE),
        SolemnLamentWeaponItem.ANIM_COLLECTION::executeAnim);
  }
}
