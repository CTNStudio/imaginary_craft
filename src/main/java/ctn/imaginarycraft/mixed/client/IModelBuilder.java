package ctn.imaginarycraft.mixed.client;

import ctn.imaginarycraft.api.NoMixinException;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;

import java.util.Map;

public interface IModelBuilder<T extends ModelBuilder<T>> {
  static <T extends ModelBuilder<T>> IModelBuilder<T> of(ModelBuilder<T> modelBuilder) {
    return (IModelBuilder<T>) modelBuilder;
  }

  default Map<String, String> imaginarycraft$getTexture() {
    throw new NoMixinException();
  }
}
