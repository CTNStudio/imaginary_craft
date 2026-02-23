package ctn.imaginarycraft.core.capability.entity;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IEntityLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevelType getLcLevel();
}
