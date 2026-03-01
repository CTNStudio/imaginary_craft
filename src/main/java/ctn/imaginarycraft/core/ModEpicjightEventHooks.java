package ctn.imaginarycraft.core;

import ctn.imaginarycraft.common.world.item.ego.weapon.melee.special.RedEyesTachiItem;
import yesman.epicfight.api.event.EpicFightEventHooks;

public final class ModEpicjightEventHooks {

  static void listenerRegister() {
    skill();
  }

  private static void skill() {
    EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(event -> {
      RedEyesTachiItem.phaseSwitch(event);
    });
//    EpicFightEventHooks.Player.CAST_SKILL.registerContextAwareEvent(event -> {
//
//    });
  }

  private ModEpicjightEventHooks() {
  }
}
