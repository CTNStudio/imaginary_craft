package ctn.imaginarycraft.core.capability.block;

import ctn.imaginarycraft.api.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

/**
 * 可以通过继承该接口实现自定义LcLevel获取但仍然推荐通过能力系统注册
 */
public interface IBlockLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevel getLcLevel(Level level, BlockPos pos);
}
