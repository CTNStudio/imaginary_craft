package ctn.imaginarycraft.core.capability.entity;

import ctn.imaginarycraft.api.LcLevel;
import org.jetbrains.annotations.Nullable;

/**
 * 可以通过继承该接口实现自定义LcLevel获取但仍然推荐通过能力系统注册
 */
public interface IEntityLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevel getLcLevel();
}
