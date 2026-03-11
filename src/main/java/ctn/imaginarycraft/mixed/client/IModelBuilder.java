package ctn.imaginarycraft.mixed.client;

import ctn.imaginarycraft.api.*;
import net.neoforged.neoforge.client.model.generators.*;

import java.util.*;

public interface IModelBuilder<T extends ModelBuilder<T>> {
  static <T extends ModelBuilder<T>> IModelBuilder<T> of(ModelBuilder<T> modelBuilder) {
    return (IModelBuilder<T>) modelBuilder;
  }

  default Map<String, String> imaginarycraft$getTexture() {
    throw new NoMixinException();
  }
}
