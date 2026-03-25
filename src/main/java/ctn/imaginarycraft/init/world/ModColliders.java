package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.main.EpicFightMod;

public final class ModColliders {
  public static final Collider TENTACLE = ColliderPreset.registerCollider(ImaginaryCraft.modRl("tentacle"),
    new MultiOBBCollider(2, 0.4D, 0.6D, 0.4D, 0.0D, 0.3D, 0.0D));

  public static void init() {
  }
}
