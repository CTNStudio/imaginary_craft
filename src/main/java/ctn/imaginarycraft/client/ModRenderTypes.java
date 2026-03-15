package ctn.imaginarycraft.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class ModRenderTypes {
  public static final BiFunction<ResourceLocation, RenderStateShard.TransparencyStateShard, RenderType> MAGIC_BULLET_MAGIC_CIRCLE = Util.memoize(
    (resourceLocation, transparencyStateShard) -> RenderType.create(
      "imaginarycraft:magic_bullet_magic_circle",
      DefaultVertexFormat.NEW_ENTITY,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
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

  public static final Function<ResourceLocation, ParticleRenderType> TRAIL_EFFECT_LIGHT = Util.memoize(textureLocation -> {
    TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
    AbstractTexture abstracttexture = texturemanager.getTexture(textureLocation);

    // Set texture parameter
    RenderSystem.bindTexture(abstracttexture.getId());
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

    return new ParticleRenderType() {
      @Override
      public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthMask(false);
        RenderSystem.setShaderTexture(0, textureLocation);
//        RenderSystem.setShader(GameRenderer::getParticleShader);

        return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
      }

      @Override
      public String toString() {
        return "epicfight:TRAIL_EFFECT";
      }
    };
  });
}
