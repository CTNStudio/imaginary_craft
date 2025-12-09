package ctn.imaginarycraft.api.capability.entity;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IEntityLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevel getLcLevel();
}
