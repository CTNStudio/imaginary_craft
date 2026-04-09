package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import ctn.imaginarycraft.common.world.entity.ordeals.amber.IOrdealsAmberEntity;
import ctn.imaginarycraft.common.world.entity.ordeals.crimson.IOrdealsCrimsonEntity;
import ctn.imaginarycraft.common.world.entity.ordeals.green.IOrdealsGreenEntity;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.IOrdealsVioletEntity;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.tag.ModEntityTags;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public final class DatagenEntityTag extends EntityTypeTagsProvider {
  public DatagenEntityTag(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, provider, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    var entries = OrdealsEntityTypes.REGISTRY.getEntries();
    tag(ModEntityTags.ORDEALS_VIOLET).add(getArray(entries, IOrdealsVioletEntity.class));
    tag(ModEntityTags.ORDEALS_AMBER).add(getArray(entries, IOrdealsAmberEntity.class));
    tag(ModEntityTags.ORDEALS_GREEN).add(getArray(entries, IOrdealsGreenEntity.class));
    tag(ModEntityTags.ORDEALS_CRIMSON).add(getArray(entries, IOrdealsCrimsonEntity.class));
    tag(ModEntityTags.ORDEALS).addTags(ModEntityTags.ORDEALS_VIOLET, ModEntityTags.ORDEALS_AMBER, ModEntityTags.ORDEALS_GREEN, ModEntityTags.ORDEALS_CRIMSON);
  }

  private static EntityType<?>[] getArray(Collection<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> entries, Class<? extends IOrdealsEntity> clazz) {
    return entries.stream()
      .filter(entityType -> clazz.isInstance(entityType.get()))
      .map(DeferredHolder::get)
      .toArray(EntityType[]::new);
  }
}
