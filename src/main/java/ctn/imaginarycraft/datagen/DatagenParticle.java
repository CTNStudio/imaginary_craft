package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.client.particle.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.*;
import net.minecraft.core.particles.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.common.data.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class DatagenParticle extends ParticleDescriptionProvider {
  public DatagenParticle(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  private static @NotNull ResourceLocation getPath(String name) {
    return ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name);
  }

  @Override
  protected void addDescriptions() {
    sprite(ModParticleTypes.LC_DAMAGE_ICON, Arrays.stream(LcDamageIconParticle.Type.values())
      .map(LcDamageIconParticle.Type::getTexturePl)
      .toArray(String[]::new));
    sprite(ModParticleTypes.DYEING_MAGIC_CIRCLE, "magic_circle/magic_circle16x", "magic_circle/magic_circle32x", "magic_circle/magic_circle128x");
    sprite(ModParticleTypes.MAGIC_BULLET_MAGIC_CIRCLE, "magic_bullet/magic_circle16x", "magic_bullet/magic_circle32x", "magic_bullet/magic_circle128x");
    sprite(ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_BLACK, "solemn_lament/butterfly_black");
    sprite(ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_WHITE, "solemn_lament/butterfly_white");
  }

  private <T extends ParticleType<?>> void sprite(Supplier<T> type, String name) {
    sprite(type.get(), ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, name));
  }

  private <T extends ParticleType<?>> void sprite(Supplier<T> type, String... names) {
    spriteSet(type.get(), Arrays.stream(names)
      .map(DatagenParticle::getPath)
      .collect(Collectors.toList()));
  }
}
