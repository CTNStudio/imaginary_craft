package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoArmorItems;
import ctn.imaginarycraft.init.item.ego.EgoCurioItems;
import ctn.imaginarycraft.init.item.ego.weapon.EgoSpecialWeaponItems;
import ctn.imaginarycraft.init.item.ego.weapon.EgoWeaponItems;
import ctn.imaginarycraft.init.util.CreativeModeTabRegisterUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 创造模式物品栏
 */
public final class ModCreativeModeTabs extends CreativeModeTabRegisterUtil {
  public static final DeferredRegister<CreativeModeTab> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.CREATIVE_MODE_TAB);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_CURIOS = register(
    "ego_curios", "E.G.O 饰品", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      EgoCurioItems.REGISTRY.getEntries().forEach(entry -> output.accept(entry.get())), () ->
      EgoCurioItems.BENEDICTION.get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_WEAPON = register(
    "ego_weapon", "E.G.O 武器", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      EgoWeaponItems.REGISTRY.getEntries().forEach(entry -> output.accept(entry.get())), () ->
      EgoSpecialWeaponItems.BENEDICTION.get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_ARMOR = register(
    "ego_armor", "E.G.O 护甲", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      EgoArmorItems.REGISTRY.getEntries().forEach(entry -> output.accept(entry.get())), () ->
      EgoArmorItems.BENEDICTION.get().getDefaultInstance()));
}
