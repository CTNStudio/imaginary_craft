package ctn.imaginarycraft.mixinextend.client;

import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

public interface IModelBuilderMixin<T extends ModelBuilder<T>> {
  static <T extends ModelBuilder<T>> IModelBuilderMixin<T> of(ModelBuilder<T> modelBuilder) {
    return (IModelBuilderMixin<T>) modelBuilder;
  }

  @Unique
  Map<String, String> imaginarycraft$getTexture();
}
