package ctn.imaginarycraft.client;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public final class ModParticleRenderTypes {
  public static final ParticleRenderType LOBOTOMY_CORPORATION_DAMAGE_ICON_PARTICLE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.disableDepthTest();
      RenderSystem.setShader(GameRenderer::getParticleShader);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "LOBOTOMY_CORPORATION_DAMAGE_ICON_PARTICLE";
    }
  };

  public static final ParticleRenderType TEXT_PARTICLE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "TEXT_PARTICLE";
    }
  };

  public static final ParticleRenderType MAGIC_CIRCLE_PARTICLE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.depthMask(true);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
      RenderSystem.disableCull();
      RenderSystem.defaultBlendFunc();
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "MAGIC_CIRCLE_PARTICLE";
    }

    @Override
    public boolean isTranslucent() {
      return false;
    }
  };
}
