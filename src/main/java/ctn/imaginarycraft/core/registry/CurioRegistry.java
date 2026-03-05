package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.datagen.*;
import ctn.imaginarycraft.init.tag.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;

import static top.theillusivec4.curios.api.CuriosApi.*;

/**
 * 饰品
 */
public final class CurioRegistry {
  public static void registry() {
    createValidators(DatagenCuriosTest.EGO_CURIOS_VALIDATOR, ModItemTags.EGO_CURIOS);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HEADWEAR_VALIDATOR, ModItemTags.EGO_CURIOS_HEADWEAR);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HEAD_VALIDATOR, ModItemTags.EGO_CURIOS_HEAD);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HINDBRAIN_VALIDATOR, ModItemTags.EGO_CURIOS_HINDBRAIN);
    createValidators(DatagenCuriosTest.EGO_CURIOS_EYE_VALIDATOR, ModItemTags.EGO_CURIOS_EYE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_FACE_VALIDATOR, ModItemTags.EGO_CURIOS_FACE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_CHEEK_VALIDATOR, ModItemTags.EGO_CURIOS_CHEEK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_MASK_VALIDATOR, ModItemTags.EGO_CURIOS_MASK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_MOUTH_VALIDATOR, ModItemTags.EGO_CURIOS_MOUTH);
    createValidators(DatagenCuriosTest.EGO_CURIOS_NECK_VALIDATOR, ModItemTags.EGO_CURIOS_NECK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_BROOCH_VALIDATOR, ModItemTags.EGO_CURIOS_BROOCH);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HAND_VALIDATOR, ModItemTags.EGO_CURIOS_HAND);
    createValidators(DatagenCuriosTest.EGO_CURIOS_GLOVE_VALIDATOR, ModItemTags.EGO_CURIOS_GLOVE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK_VALIDATOR, ModItemTags.EGO_CURIOS_RIGHT_BACK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_LEFT_BACK_VALIDATOR, ModItemTags.EGO_CURIOS_LEFT_BACK);
  }

  private static void createValidators(ResourceLocation name, TagKey<Item> tagKey) {
    registerCurioPredicate(name, (slotResult) -> slotResult.stack().is(tagKey));
  }
}
