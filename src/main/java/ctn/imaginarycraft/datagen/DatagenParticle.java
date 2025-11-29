package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.client.particle.TextParticleProvider;
import ctn.imaginarycraft.client.particle.TextParticlesType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class DatagenParticle extends ParticleDescriptionProvider {
  public DatagenParticle(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  private static @NotNull ResourceLocation getPath(String name) {
    return ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name);
  }

  @Override
  protected void addDescriptions() {
    createSprite(ModParticleTypes.TEXT_PARTICLE_TYPE, Arrays.stream(TextParticlesType.values())
      .map(TextParticlesType::getTexturePl).toArray(String[]::new));
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String name) {
    sprite(type.get(), ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name));
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String... names) {
    List<ResourceLocation> list = new ArrayList<>();
    for (String name : names) {
      list.add(getPath(name));
    }
    spriteSet(type.get(), list);
  }
}
