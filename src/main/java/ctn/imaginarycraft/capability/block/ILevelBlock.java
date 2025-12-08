package ctn.imaginarycraft.capability.block;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.CheckForNull;

public interface ILevelBlock {
  @CheckForNull
  LcLevel getItemLevel(BlockState state);
}
