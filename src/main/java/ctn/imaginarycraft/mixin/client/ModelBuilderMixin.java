package ctn.imaginarycraft.mixin.client;

import ctn.imaginarycraft.mixed.client.*;
import net.neoforged.neoforge.client.model.generators.*;
import org.spongepowered.asm.mixin.*;

import java.util.*;

@Mixin(ModelBuilder.class)
public abstract class ModelBuilderMixin<T extends ModelBuilder<T>> implements IModelBuilder<T> {
  @Shadow
  @Final
  protected Map<String, String> textures;

  @Unique
  @Override
  public Map<String, String> imaginarycraft$getTexture() {
    return textures;
  }
}
