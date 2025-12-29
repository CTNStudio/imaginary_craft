package ctn.imaginarycraft.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

public final class ModRenderTypes {
  public static final BiFunction<ResourceLocation, RenderStateShard.TransparencyStateShard, RenderType> MAGIC_BULLET_MAGIC_CIRCLE = Util.memoize(
    (resourceLocation, transparencyStateShard) -> RenderType.create(
      "magic_bullet_magic_circle", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true,
      RenderType.CompositeState.builder()
        .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
        .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
        .setTransparencyState(transparencyStateShard)
        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
        .setCullState(RenderStateShard.NO_CULL)
        .createCompositeState(true)));

  public static RenderType magicBulletMagicCircle(ResourceLocation location) {
    return MAGIC_BULLET_MAGIC_CIRCLE.apply(location, RenderStateShard.ADDITIVE_TRANSPARENCY);
  }
}
