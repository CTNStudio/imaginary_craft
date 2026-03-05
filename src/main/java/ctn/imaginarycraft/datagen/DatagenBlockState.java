package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.*;

public final class DatagenBlockState extends BlockStateProvider {
  public DatagenBlockState(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, ImaginaryCraft.ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {

  }
}
