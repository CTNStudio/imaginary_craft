package ctn.imaginarycraft.api.world.entity;

public interface IAbnormalitiesEntity {
  /**
   * 需要特化刷怪蛋生成时调用
   */
  default void doWhenSpawnByEggs() {

  }
}
