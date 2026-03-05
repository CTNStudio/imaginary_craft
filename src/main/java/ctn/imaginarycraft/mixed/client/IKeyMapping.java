package ctn.imaginarycraft.mixed.client;

import net.minecraft.client.*;

public interface IKeyMapping {
  static IKeyMapping of(KeyMapping obj) {
    return (IKeyMapping) obj;
  }

  int imaginarycraft$getClickCount();
}
