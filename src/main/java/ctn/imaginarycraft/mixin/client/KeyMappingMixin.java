package ctn.imaginarycraft.mixin.client;

import ctn.imaginarycraft.mixed.client.*;
import net.minecraft.client.*;
import net.neoforged.neoforge.client.extensions.*;
import org.spongepowered.asm.mixin.*;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin implements Comparable<KeyMapping>, IKeyMappingExtension, IKeyMapping {
  @Shadow
  private int clickCount;

  @Override
  public int imaginarycraft$getClickCount() {
    return clickCount;
  }
}
