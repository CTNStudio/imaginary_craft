package ctn.imaginarycraft.api.world.entity;

import net.minecraft.world.entity.Entity;

public interface ISpawnByEgg {
  /**
   * 需要特化刷怪蛋生成时调用
   */
  void onSpawnByEgg();
}
