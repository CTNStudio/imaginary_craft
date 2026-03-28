package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.gameasset.ColliderPreset;

public final class ModColliders {
  public static final Collider TENTACLE = ColliderPreset.registerCollider(ImaginaryCraft.modRl("tentacle"),
    new MultiOBBCollider(2, 0.4D, 2D, 0.4D, 0.0D, 0.6D, 0.0D));
  public static final Collider GRANT_US_LOVE_TENTACLE_SLASH = ColliderPreset.registerCollider(ImaginaryCraft.modRl("grant_us_love_tentacle_slash"),
    new MultiOBBCollider(1, 3.5D, 1D, 3.5D, 0.0D, 0.6D, 0.0D));

  public static void init() {
  }
}
