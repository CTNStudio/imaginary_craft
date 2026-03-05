package ctn.imaginarycraft.mixin;

import net.neoforged.neoforge.client.model.generators.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(ModelFile.class)
public interface ModelFileMixin {
  @Invoker("exists")
  boolean getExists();
}
