package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.ImaginaryCraftConstants;
import ctn.imaginarycraft.init.ModWeaponCapabilityPresets;
import ctn.imaginarycraft.init.world.item.ego.EgoWeaponItems;
import ctn.imaginarycraft.mixin.WeaponTypeReloadListenerMixin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;

import java.util.Set;
import java.util.function.Function;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class EpicFightRegistry {
  @SubscribeEvent
  public static void animationsRegistry(AnimationManager.AnimationRegistryEvent event) {
    event.newBuilder(ImaginaryCraft.ID, builder -> {
      Armatures.ArmatureAccessor<HumanoidArmature> armatureAccessor = Armatures.BIPED;
    });
  }

  @SubscribeEvent
  public static void register(FMLCommonSetupEvent event) {
    event.enqueueWork(EpicFightRegistry::registerWeaponType);
    event.enqueueWork(EpicFightRegistry::registerWeaponTypesByClass);
  }

  private static void registerWeaponType() {
    register(ImaginaryCraft.modRl("hammer"), ModWeaponCapabilityPresets.HAMMER);
    register(ImaginaryCraft.modRl("mace"), ModWeaponCapabilityPresets.MACE);
    register(ImaginaryCraft.modRl("cannon"), ModWeaponCapabilityPresets.CANNON);
    register(ImaginaryCraft.modRl("gun"), ModWeaponCapabilityPresets.GUN);
    register(ImaginaryCraft.modRl("pistol"), ModWeaponCapabilityPresets.PISTOL);
    register(ImaginaryCraft.modRl("rifle"), ModWeaponCapabilityPresets.RIFLE);
  }

  private static void registerWeaponTypesByClass() {
    register(ImaginaryCraftConstants.GUN, ModWeaponCapabilityPresets.GUN);
    register(ImaginaryCraftConstants.PISTOL, ModWeaponCapabilityPresets.PISTOL);
    register(ImaginaryCraftConstants.RIFLE, ModWeaponCapabilityPresets.RIFLE);
    register(ImaginaryCraftConstants.CANNON, ModWeaponCapabilityPresets.CANNON);
    register(ImaginaryCraftConstants.CROSSBOW, WeaponCapabilityPresets.CROSSBOW);
    register(ImaginaryCraftConstants.BOW, WeaponCapabilityPresets.BOW);
    register(ImaginaryCraftConstants.KNIFE, WeaponCapabilityPresets.DAGGER);
    register(ImaginaryCraftConstants.HAMMER, ModWeaponCapabilityPresets.HAMMER);
    register(ImaginaryCraftConstants.FIST, WeaponCapabilityPresets.FIST);
    register(ImaginaryCraftConstants.SPEAR, WeaponCapabilityPresets.SPEAR);
    register(ImaginaryCraftConstants.AXE, WeaponCapabilityPresets.AXE);
    register(ImaginaryCraftConstants.MACE, ModWeaponCapabilityPresets.MACE);
    register(ImaginaryCraftConstants.SWORDS, WeaponCapabilityPresets.SWORD);
    register(EgoWeaponItems.RED_EYES_TACHI.get(), WeaponCapabilityPresets.TACHI);
  }

  private static void register(Set<DeferredItem<? extends Item>> items, Function<Item, ? extends CapabilityItem.Builder<?>> builder) {
    for (DeferredItem<? extends Item> item : items) {
      Item item1 = item.asItem();
      register(item1, builder);
    }
  }

  private static void register(Item item1, Function<Item, ? extends CapabilityItem.Builder<?>> builder) {
    EpicFightCapabilities.ITEM_CAPABILITY_PROVIDER.put(item1, builder.apply(item1).build());
  }

  private static <T extends CapabilityItem.Builder<?>> void register(ResourceLocation rl, Function<Item, T> builder) {
    WeaponTypeReloadListenerMixin.getPresets().put(rl, builder);
  }
}
