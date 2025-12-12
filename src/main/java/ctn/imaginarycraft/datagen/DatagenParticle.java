package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.client.particle.LcDamageIconParticle;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class DatagenParticle extends ParticleDescriptionProvider {
  public DatagenParticle(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  private static @NotNull ResourceLocation getPath(String name) {
    return ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name);
  }

  @Override
  protected void addDescriptions() {
    createSprite(ModParticleTypes.LC_DAMAGE_ICON, Arrays.stream(LcDamageIconParticle.Type.values())
      .map(LcDamageIconParticle.Type::getTexturePl)
      .toArray(String[]::new));
    createSprite(ModParticleTypes.DYEING_MAGIC_CIRCLE, "magic_circle/magic_circle16x", "magic_circle/magic_circle32x", "magic_circle/magic_circle128x");
    createSprite(ModParticleTypes.MAGIC_BULLET_MAGIC_CIRCLE, "magic_bullet/magic_circle16x", "magic_bullet/magic_circle32x", "magic_bullet/magic_circle128x");
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String name) {
    sprite(type.get(), ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name));
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String... names) {
    spriteSet(type.get(), Arrays.stream(names)
      .map(DatagenParticle::getPath)
      .collect(Collectors.toList()));
  }
}
