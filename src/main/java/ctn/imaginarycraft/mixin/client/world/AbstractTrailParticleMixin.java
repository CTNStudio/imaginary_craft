package ctn.imaginarycraft.mixin.client.world;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.mixed.client.IAbstractTrailParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;

@Mixin(AbstractTrailParticle.class)
public abstract class AbstractTrailParticleMixin extends TextureSheetParticle implements IAbstractTrailParticle {
  @Unique
  private Integer imaginarycraft$textureLightId;

  protected AbstractTrailParticleMixin(ClientLevel level, double x, double y, double z) {
    super(level, x, y, z);
  }

  @Inject(method = "<init>*", at = @At("RETURN"))
  private void imaginarycraft$init(@Local(name = "trailInfo") TrailInfo trailInfo) {
    String path = trailInfo.texturePath().toString();
    ResourceLocation textureLight = ResourceLocation.parse(path.substring(0, path.lastIndexOf(".png")) + "_light.png");
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.getResourceManager().getResource(textureLight).isPresent()) {
      TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
      AbstractTexture abstracttexture = texturemanager.getTexture(textureLight);
      imaginarycraft$textureLightId = abstracttexture.getId();
    }
  }

  @Inject(method = "render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V", at = @At("HEAD"))
  private void enhanceRendering(CallbackInfo ci) {
//    if (imaginarycraft$textureLightId == null) {
//      return;
//    }
  }

  @Inject(
    method = "render", at = @At(value = "INVOKE", ordinal = 3, shift = At.Shift.AFTER,
    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setLight(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
  private void forceEmissiveLight(
    VertexConsumer vertexConsumer,
    Camera camera,
    float partialTick,
    CallbackInfo ci,
    @Local(name = "light") int light,
    @Local(name = "pos1") Vector4f pos1,
    @Local(name = "pos2") Vector4f pos2,
    @Local(name = "pos3") Vector4f pos3,
    @Local(name = "pos4") Vector4f pos4,
    @Local(name = "alphaFrom") float alphaFrom,
    @Local(name = "alphaTo") float alphaTo,
    @Local(name = "fading") float fading,
    @Local(name = "from") float from,
    @Local(name = "to") float to
  ) {
    if (imaginarycraft$textureLightId == null) {
      return;
    }
    RenderSystem.disableCull();
    RenderSystem.depthMask(false);

    RenderSystem.setShaderColor(2, 2, 2, 2);

    RenderSystem.blendFuncSeparate(
      GlStateManager.SourceFactor.ONE,
      GlStateManager.DestFactor.SRC_ALPHA,
      GlStateManager.SourceFactor.ONE,
      GlStateManager.DestFactor.SRC_COLOR
    );
    light = LightTexture.FULL_BRIGHT;
    RenderSystem.bindTexture(imaginarycraft$textureLightId);
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    vertexConsumer.addVertex(pos1.x(), pos1.y(), pos1.z()).setUv(from, 1.0F).setColor(this.rCol, this.gCol, this.bCol, this.alpha * alphaFrom * fading).setLight(light);
    vertexConsumer.addVertex(pos2.x(), pos2.y(), pos2.z()).setUv(from, 0.0F).setColor(this.rCol, this.gCol, this.bCol, this.alpha * alphaFrom * fading).setLight(light);
    vertexConsumer.addVertex(pos3.x(), pos3.y(), pos3.z()).setUv(to, 0.0F).setColor(this.rCol, this.gCol, this.bCol, this.alpha * alphaTo * fading).setLight(light);
    vertexConsumer.addVertex(pos4.x(), pos4.y(), pos4.z()).setUv(to, 1.0F).setColor(this.rCol, this.gCol, this.bCol, this.alpha * alphaTo * fading).setLight(light);
  }
}
