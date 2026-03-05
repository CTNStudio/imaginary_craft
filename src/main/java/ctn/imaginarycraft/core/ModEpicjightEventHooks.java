package ctn.imaginarycraft.core;

import ctn.imaginarycraft.common.world.item.ego.weapon.melee.special.*;

public final class ModEpicjightEventHooks {

  static void listenerRegister() {
    skill();
  }

  private static void skill() {
    RedEyesTachiItem.phaseSwitch();
  }

  private ModEpicjightEventHooks() {
  }
}
