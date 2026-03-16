package ctn.imaginarycraft.api.world.entity;

import net.minecraft.world.entity.Entity;

public interface IAbnormalitiesEntity {
  /**
   * 需要特化刷怪蛋生成时调用
   */
  default void onSpawnByEgg() {

  }

  default boolean isEtHnicGroup(Entity entity) {
    return entity.getType().equals(((Entity) this).getType());
  }
}
