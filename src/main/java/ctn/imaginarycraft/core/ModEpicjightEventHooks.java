package ctn.imaginarycraft.core;

import ctn.imaginarycraft.common.world.item.ego.weapon.melee.special.RedEyesTachiItem;

public final class ModEpicjightEventHooks {

  private ModEpicjightEventHooks() {
  }

  static void listenerRegister() {
	  RedEyesTachiItem.phaseSwitch();
  }
}
