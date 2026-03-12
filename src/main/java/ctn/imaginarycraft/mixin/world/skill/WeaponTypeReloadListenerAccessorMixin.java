package ctn.imaginarycraft.mixin.world.skill;

import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.*;
import java.util.function.*;

@Mixin(WeaponTypeReloadListener.class)
public interface WeaponTypeReloadListenerAccessorMixin {

  @Accessor("PRESETS")
  static Map<ResourceLocation, Function<Item, ? extends CapabilityItem.Builder<?>>> imaginarycraft$getPresets() {
    throw new AssertionError();
  }
}
