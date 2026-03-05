package ctn.imaginarycraft.core.capability.entity;

import ctn.imaginarycraft.api.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface IEntityLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevelType getLcLevel();
}
