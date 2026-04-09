package ctn.imaginarycraft.mixin.client.world;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.mixed.client.IAbstractTrailParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

@Mixin(AbstractTrailParticle.class)
public abstract class AbstractTrailParticleMixin extends TextureSheetParticle implements IAbstractTrailParticle {
  @Shadow
  @Final
  protected TrailInfo trailInfo;
  @Unique
  private Integer imaginarycraft$textureLightId;
  @Unique
  private ResourceLocation imaginarycraft$textureLight;

  protected AbstractTrailParticleMixin(ClientLevel level, double x, double y, double z) {
    super(level, x, y, z);
  }

  @Inject(method = "<init>(Lyesman/epicfight/world/capabilities/entitypatch/EntityPatch;Lyesman/epicfight/api/client/animation/property/TrailInfo;)V", at = @At("RETURN"))
  private void imaginarycraft$init(EntityPatch entitypatch, TrailInfo trailInfo, CallbackInfo ci) {
    imaginarycraft$init(trailInfo);
  }

  @Unique
  private void imaginarycraft$init(TrailInfo trailInfo) {
    String path = trailInfo.texturePath().toString();
    ResourceLocation textureLight = ResourceLocation.parse(path.substring(0, path.lastIndexOf(".png")) + "_glowmask.png");
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.getResourceManager().getResource(textureLight).isPresent()) {
      TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
      AbstractTexture abstracttexture = texturemanager.getTexture(textureLight);
      imaginarycraft$textureLightId = abstracttexture.getId();
      imaginarycraft$textureLight = textureLight;
    }
  }

  @Inject(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;Lyesman/epicfight/world/capabilities/entitypatch/EntityPatch;Lyesman/epicfight/api/client/animation/property/TrailInfo;)V", at = @At("RETURN"))
  private void imaginarycraft$init(ClientLevel level, EntityPatch<?> entitypatch, TrailInfo trailInfo, CallbackInfo ci) {
    imaginarycraft$init(trailInfo);
  }

  @Inject(method = "getRenderType", at = @At("HEAD"))
  public void imaginarycraft$getRenderType(CallbackInfoReturnable<ParticleRenderType> cir) {
    if (imaginarycraft$textureLightId == null) {
      return;
    }
    RenderSystem.bindTexture(imaginarycraft$textureLightId);
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
    RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
  }

  @Inject(
    method = "render", at = @At(value = "INVOKE", ordinal = 3, shift = At.Shift.AFTER,
    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setLight(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
  private void imaginarycraft$forceEmissiveLight(
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
    RenderSystem.setShaderColor(2, 2, 2, 1);

    RenderSystem.blendFuncSeparate(
      GlStateManager.SourceFactor.ONE,
      GlStateManager.DestFactor.SRC_ALPHA,
      GlStateManager.SourceFactor.ONE,
      GlStateManager.DestFactor.SRC_COLOR);
    int light1 = LightTexture.FULL_BRIGHT;

    RenderSystem.setShaderTexture(0, imaginarycraft$textureLight);
    float alpha1 = alpha * alphaFrom * fading;
    float alpha2 = alpha * alphaTo * fading;
    vertexConsumer.addVertex(pos1.x(), pos1.y(), pos1.z())
      .setUv(from, 1.0F)
      .setColor(rCol, gCol, bCol, alpha1).
      setLight(light1);
    vertexConsumer.addVertex(pos2.x(), pos2.y(), pos2.z())
      .setUv(from, 0.0F)
      .setColor(rCol, gCol, bCol, alpha1)
      .setLight(light1);
    vertexConsumer.addVertex(pos3.x(), pos3.y(), pos3.z())
      .setUv(to, 0.0F)
      .setColor(rCol, gCol, bCol, alpha2)
      .setLight(light1);
    vertexConsumer.addVertex(pos4.x(), pos4.y(), pos4.z())
      .setUv(to, 1.0F)
      .setColor(rCol, gCol, bCol, alpha2)
      .setLight(light1);

    RenderSystem.setShaderColor(1, 1, 1, 1);
    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    RenderSystem.setShaderTexture(0, trailInfo.texturePath());
  }
}
