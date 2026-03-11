package ctn.imaginarycraft.mixed.client;

import ctn.imaginarycraft.api.*;
import net.minecraft.client.*;

public interface IKeyMapping {
  static IKeyMapping of(KeyMapping obj) {
    return obj;
  }

  default int imaginarycraft$getClickCount() {
    throw new NoMixinException();
  }
}
