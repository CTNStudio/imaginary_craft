package ctn.imaginarycraft.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;

import java.util.Map;
import java.util.function.Function;

@Mixin(WeaponTypeReloadListener.class)
public interface WeaponTypeReloadListenerMixin {

  @Accessor("PRESETS")
  static Map<ResourceLocation, Function<Item, ? extends CapabilityItem.Builder<?>>> getPresets() {
    throw new AssertionError();
  }
}
