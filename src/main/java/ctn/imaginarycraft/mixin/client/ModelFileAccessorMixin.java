package ctn.imaginarycraft.mixin.client;

import net.neoforged.neoforge.client.model.generators.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(ModelFile.class)
public interface ModelFileAccessorMixin {
  @Invoker("exists")
  boolean imaginarycraft$getExists();
}
