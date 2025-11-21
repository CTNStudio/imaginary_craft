package ctn.singularity.lib.capability.block;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import net.minecraft.world.level.block.state.BlockState;

public interface IColorDamageTypeBlock {
  LcDamage getDamageType(BlockState state);
}
