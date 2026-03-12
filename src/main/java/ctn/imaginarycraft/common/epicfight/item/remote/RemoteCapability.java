package ctn.imaginarycraft.common.epicfight.item.remote;

import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;

public abstract class RemoteCapability extends RangedWeaponCapability {
  protected RemoteCapability(RangedWeaponCapability.Builder builder) {
    super(builder);
  }
}
