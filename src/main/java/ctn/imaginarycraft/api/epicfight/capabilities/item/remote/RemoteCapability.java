package ctn.imaginarycraft.api.epicfight.capabilities.item.remote;

import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;

public abstract class RemoteCapability extends RangedWeaponCapability {
  protected RemoteCapability(RangedWeaponCapability.Builder builder) {
    super(builder);
  }
}
