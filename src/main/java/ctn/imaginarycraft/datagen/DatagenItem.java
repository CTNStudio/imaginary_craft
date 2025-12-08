package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ModEgoCurios;
import ctn.imaginarycraft.mixinextend.client.IModelBuilderMixin;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class DatagenItem extends ItemModelProvider {
  public DatagenItem(final PackOutput output, final ExistingFileHelper existingFileHelper) {
    super(output, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    ModEgoCurios.REGISTRY.getEntries().forEach(item -> {
      ItemModelBuilder itemModelBuilder = this.withExistingParent(item.getId().getPath(), "item/generated");
      IModelBuilderMixin.of(itemModelBuilder).imaginarycraft$getTexture()
        .put("layer0", item.getId().withPrefix("item/curios/").toString());
    });
  }
}
