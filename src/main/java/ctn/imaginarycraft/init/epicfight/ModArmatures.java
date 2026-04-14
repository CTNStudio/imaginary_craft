package ctn.imaginarycraft.init.epicfight;

import ctn.imaginarycraft.client.animmodels.armature.GrantUsLoveArmature;
import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Armatures;

/**
 * 骨骼
 */
public final class ModArmatures {
  public static final Armatures.ArmatureAccessor<GrantUsLoveArmature> GRANT_US_LOVE = create("entity/grant_us_love", GrantUsLoveArmature::new);

  public static void init() {
  }

  private static <T extends Armature> Armatures.ArmatureAccessor<T> create(String name, Armatures.ArmatureContructor<T> constructor) {
    return Armatures.ArmatureAccessor.create(ImaginaryCraft.ID, name, constructor);
  }
}
