package ctn.singularity.lib.capability.block;

import ctn.singularity.lib.api.lobotomycorporation.LcLevel;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.CheckForNull;

public interface ILevelBlock {
  @CheckForNull
  LcLevel getItemLevel(BlockState state);
}
