package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.data.IEntitiesData;
import top.theillusivec4.curios.api.type.data.ISlotData;

import java.util.concurrent.CompletableFuture;

public final class DatagenCuriosTest extends CuriosDataProvider {
  public static final String EGO_CURIOS_HEADWEAR = "ego_curios_headwear";
  public static final String EGO_CURIOS_HEAD = "ego_curios_head";
  public static final String EGO_CURIOS_HINDBRAIN = "ego_curios_hindbrain";
  public static final String EGO_CURIOS_EYE = "ego_curios_eye";
  public static final String EGO_CURIOS_FACE = "ego_curios_face";
  public static final String EGO_CURIOS_CHEEK = "ego_curios_cheek";
  public static final String EGO_CURIOS_MASK = "ego_curios_mask";
  public static final String EGO_CURIOS_MOUTH = "ego_curios_mouth";
  public static final String EGO_CURIOS_NECK = "ego_curios_neck";
  public static final String EGO_CURIOS_BROOCH = "ego_curios_brooch";
  public static final String EGO_CURIOS_HAND = "ego_curios_hand";
  public static final String EGO_CURIOS_GLOVE = "ego_curios_glove";
  public static final String EGO_CURIOS_RIGHT_BACK = "ego_curios_right_back";
  public static final String EGO_CURIOS_LEFT_BACK = "ego_curios_left_back";

  public static final ResourceLocation EGO_CURIOS_VALIDATOR = createTagId("ego_curios");
  public static final ResourceLocation EGO_CURIOS_HEADWEAR_VALIDATOR = createTagId("ego_curios_headwear");
  public static final ResourceLocation EGO_CURIOS_HEAD_VALIDATOR = createTagId("ego_curios_head");
  public static final ResourceLocation EGO_CURIOS_HINDBRAIN_VALIDATOR = createTagId("ego_curios_hindbrain");
  public static final ResourceLocation EGO_CURIOS_EYE_VALIDATOR = createTagId("ego_curios_eye");
  public static final ResourceLocation EGO_CURIOS_FACE_VALIDATOR = createTagId("ego_curios_face");
  public static final ResourceLocation EGO_CURIOS_CHEEK_VALIDATOR = createTagId("ego_curios_cheek");
  public static final ResourceLocation EGO_CURIOS_MASK_VALIDATOR = createTagId("ego_curios_mask");
  public static final ResourceLocation EGO_CURIOS_MOUTH_VALIDATOR = createTagId("ego_curios_mouth");
  public static final ResourceLocation EGO_CURIOS_NECK_VALIDATOR = createTagId("ego_curios_neck");
  public static final ResourceLocation EGO_CURIOS_BROOCH_VALIDATOR = createTagId("ego_curios_brooch");
  public static final ResourceLocation EGO_CURIOS_HAND_VALIDATOR = createTagId("ego_curios_hand");
  public static final ResourceLocation EGO_CURIOS_GLOVE_VALIDATOR = createTagId("ego_curios_glove");
  public static final ResourceLocation EGO_CURIOS_RIGHT_BACK_VALIDATOR = createTagId("ego_curios_right_back");
  public static final ResourceLocation EGO_CURIOS_LEFT_BACK_VALIDATOR = createTagId("ego_curios_left_back");

  public DatagenCuriosTest(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
    super(ImaginaryCraft.ID, output, fileHelper, registries);
  }

  private static String createId(String name) {
    return ImaginaryCraft.modRlText(name);
  }

  private static ResourceLocation createTagId(String name) {
    return ImaginaryCraft.modRl(name + "_tag");
  }

  @Override
  public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
    createSlot(EGO_CURIOS_HEADWEAR, EGO_CURIOS_HEADWEAR_VALIDATOR);
    createSlot(EGO_CURIOS_HEAD, EGO_CURIOS_HEAD_VALIDATOR);
    createSlot(EGO_CURIOS_HINDBRAIN, EGO_CURIOS_HINDBRAIN_VALIDATOR);
    createSlot(EGO_CURIOS_EYE, EGO_CURIOS_EYE_VALIDATOR);
    createSlot(EGO_CURIOS_FACE, EGO_CURIOS_FACE_VALIDATOR);
    createSlot(EGO_CURIOS_CHEEK, EGO_CURIOS_CHEEK_VALIDATOR);
    createSlot(EGO_CURIOS_MASK, EGO_CURIOS_MASK_VALIDATOR);
    createSlot(EGO_CURIOS_MOUTH, EGO_CURIOS_MOUTH_VALIDATOR);
    createSlot(EGO_CURIOS_NECK, EGO_CURIOS_NECK_VALIDATOR);
    createSlot(EGO_CURIOS_BROOCH, EGO_CURIOS_BROOCH_VALIDATOR);
    createSlot(EGO_CURIOS_HAND, EGO_CURIOS_HAND_VALIDATOR);
    createSlot(EGO_CURIOS_GLOVE, EGO_CURIOS_GLOVE_VALIDATOR);
    createSlot(EGO_CURIOS_RIGHT_BACK, EGO_CURIOS_RIGHT_BACK_VALIDATOR);
    createSlot(EGO_CURIOS_LEFT_BACK, EGO_CURIOS_LEFT_BACK_VALIDATOR);

    createSimpleEntities("player");
  }

  public ISlotData createSlot(String nameID, String icon, ResourceLocation validator) {
    return createSlot(nameID, validator).icon(ImaginaryCraft.modRl(icon));
  }

  public ISlotData createSlot(String nameId, ResourceLocation validator) {
    return super.createSlot(nameId)
      .dropRule(ICurio.DropRule.ALWAYS_KEEP)
      .addValidator(validator)
      .addCosmetic(true);
  }

  public IEntitiesData createSimpleEntities(String nameID) {
    return createEntities(nameID).addPlayer().addSlots(
      EGO_CURIOS_HEADWEAR,
      EGO_CURIOS_HEAD,
      EGO_CURIOS_HINDBRAIN,
      EGO_CURIOS_EYE,
      EGO_CURIOS_FACE,
      EGO_CURIOS_CHEEK,
      EGO_CURIOS_MASK,
      EGO_CURIOS_MOUTH,
      EGO_CURIOS_NECK,
      EGO_CURIOS_BROOCH,
      EGO_CURIOS_HAND,
      EGO_CURIOS_GLOVE,
      EGO_CURIOS_RIGHT_BACK,
      EGO_CURIOS_LEFT_BACK);
  }
}
