package ctn.imaginarycraft.mixed.client;

import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

public interface IModelBuilder<T extends ModelBuilder<T>> {
  static <T extends ModelBuilder<T>> IModelBuilder<T> of(ModelBuilder<T> modelBuilder) {
    return (IModelBuilder<T>) modelBuilder;
  }

  @Unique
  Map<String, String> imaginarycraft$getTexture();
}
