package ctn.imaginarycraft.init.item;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.util.ArmorMaterialsRegisterUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModArmorMaterials extends ArmorMaterialsRegisterUtil {
  public static final DeferredRegister<ArmorMaterial> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ARMOR_MATERIAL);

  // EGO 护甲
  public static final Holder<ArmorMaterial> ZAYIN = register(
    "zayin", 1, 2, 3, 1, 3, 9,
    ModSoundEvents.ARMOR_EQUIP_ZAYIN, 0.5F, 0.0F);
  public static final Holder<ArmorMaterial> TETH = register(
    "teth", 2, 5, 6, 2, 5, 9,
    ModSoundEvents.ARMOR_EQUIP_TETH, 1.0F, 0.0F);
  public static final Holder<ArmorMaterial> HE = register(
    "he", 3, 6, 8, 3, 11, 9,
    ModSoundEvents.ARMOR_EQUIP_HE, 2.0F, 0.025F);
  public static final Holder<ArmorMaterial> WAW = register(
    "waw", 4, 7, 9, 4, 12, 9,
    ModSoundEvents.ARMOR_EQUIP_WAW, 3.0F, 0.05F);
  public static final Holder<ArmorMaterial> ALEPH = register(
    "aleph", 4, 8, 10, 5, 13, 9,
    ModSoundEvents.ARMOR_EQUIP_ALEPH, 4.0F, 0.1F);

  // 其他
  public static final Holder<ArmorMaterial> SUIT = register(
    "suit", 1, 2, 3, 1, 3, 9,
    ModSoundEvents.ARMOR_EQUIP_ZAYIN, 0.0F, 0.0F);
}
