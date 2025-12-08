package ctn.imaginarycraft.capability.block;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import net.minecraft.world.level.block.state.BlockState;

public interface IColorDamageTypeBlock {
  LcDamageType getDamageType(BlockState state);
}
