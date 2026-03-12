package ctn.imaginarycraft.mixed.client;

import ctn.imaginarycraft.api.NoMixinException;
import net.minecraft.client.KeyMapping;

public interface IKeyMapping {
  static IKeyMapping of(KeyMapping obj) {
    return obj;
  }

  default int imaginarycraft$getClickCount() {
    throw new NoMixinException();
  }
}
