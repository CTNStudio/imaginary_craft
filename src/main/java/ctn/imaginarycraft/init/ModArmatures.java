package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public final class ModArmatures {
  public static final Armatures.ArmatureAccessor<HumanoidArmature> GRANT_US_LOVE = create("armatures/entity/grant_us_love", HumanoidArmature::new);

  public static void init() {
  }

  private static <T extends Armature> Armatures.ArmatureAccessor<T> create(String name, Armatures.ArmatureContructor<T> constructor) {
    return Armatures.ArmatureAccessor.create(ImaginaryCraft.ID, name, constructor);
  }
}
