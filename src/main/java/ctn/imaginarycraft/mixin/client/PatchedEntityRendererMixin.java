package ctn.imaginarycraft.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.api.event.client.PatchedEntityRendererEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(PatchedEntityRenderer.class)
public abstract class PatchedEntityRendererMixin {
  @WrapMethod(method = "render")
  private <E extends LivingEntity, T extends LivingEntityPatch<E>, R extends EntityRenderer<E>> void render(
    E entity,
    T entitypatch,
    R renderer,
    MultiBufferSource buffer,
    PoseStack poseStack,
    int packedLight,
    float partialTick,
    Operation<Void> original
  ) {
    AssetAccessor<? extends SkinnedMesh> defaultMesh = this.getDefaultMesh();
    PatchedEntityRendererEvent.Pre<E, T, R, SkinnedMesh> event = new PatchedEntityRendererEvent.Pre<>(entity, entitypatch, renderer, defaultMesh, buffer, poseStack, packedLight, partialTick);
    NeoForge.EVENT_BUS.post(event);
    if (event.isCanceled()) {
      return;
    }
    entity = event.getLivingEntity();
    entitypatch = event.getLivingEntityPatch();
    renderer = event.getEntityRenderer();
    buffer = event.getBuffer();
    poseStack = event.getPoseStack();
    packedLight = event.getPackedLight();
    partialTick = event.getPartialTick();

    original.call(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTick);
    NeoForge.EVENT_BUS.post(new PatchedEntityRendererEvent.Post<E, T, R, SkinnedMesh>(entity, entitypatch, renderer, defaultMesh, buffer, poseStack, packedLight, partialTick));
  }

  @Shadow
  public abstract AssetAccessor<? extends SkinnedMesh> getDefaultMesh();
}
