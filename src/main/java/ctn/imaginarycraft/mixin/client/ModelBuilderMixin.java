package ctn.imaginarycraft.mixin.client;

import ctn.imaginarycraft.mixinextend.client.IModelBuilderMixin;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(ModelBuilder.class)
public abstract class ModelBuilderMixin<T extends ModelBuilder<T>> implements IModelBuilderMixin<T> {
  @Shadow
  @Final
  protected Map<String, String> textures;

  @Unique
  @Override
  public Map<String, String> imaginarycraft$getTexture() {
    return textures;
  }
}
