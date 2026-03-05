package ctn.imaginarycraft.mixin;

import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.*;
import java.util.function.*;

@Mixin(WeaponTypeReloadListener.class)
public interface WeaponTypeReloadListenerMixin {

  @Accessor("PRESETS")
  static Map<ResourceLocation, Function<Item, ? extends CapabilityItem.Builder<?>>> getPresets() {
    throw new AssertionError();
  }
}
