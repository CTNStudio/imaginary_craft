package ctn.imaginarycraft.mixed.client;

import net.neoforged.neoforge.client.model.generators.*;
import org.spongepowered.asm.mixin.*;

import java.util.*;

public interface IModelBuilder<T extends ModelBuilder<T>> {
  static <T extends ModelBuilder<T>> IModelBuilder<T> of(ModelBuilder<T> modelBuilder) {
    return (IModelBuilder<T>) modelBuilder;
  }

  @Unique
  Map<String, String> imaginarycraft$getTexture();
}
