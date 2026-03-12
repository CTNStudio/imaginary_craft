package ctn.imaginarycraft.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.special.RedEyesTachiItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RedEyesTachiItemWeaponRenderer extends ModGeoItemRenderer<RedEyesTachiItem> {
  public static final @NotNull ResourceLocation GLOWING_TEXTURE = ImaginaryCraft.modRl("textures/geo/item/weapon/red_eyes_tachi_shine.png");
  public static final @NotNull ResourceLocation GEO_HUNTIOG_TEXTURE = ImaginaryCraft.modRl("textures/geo/item/weapon/red_eyes_tachi_hunting.png");
  public static final @NotNull ResourceLocation HUNTIOG_TEXTURE = ImaginaryCraft.modRl("textures/item/weapon/red_eyes_tachi_hunting.png");
  private boolean isHunting;
  private LivingEntity livingEntity;

  public RedEyesTachiItemWeaponRenderer(GeoModel<RedEyesTachiItem> model, @Nullable GeoModel<RedEyesTachiItem> guiModel) {
    super(model, guiModel);
    RedEyesTachiItemWeaponRenderer weaponRenderer = RedEyesTachiItemWeaponRenderer.this;
    addRenderLayer(new AutoGlowingGeoLayer<>(this) {
      @Override
      protected RenderType getRenderType(RedEyesTachiItem animatable, @Nullable MultiBufferSource bufferSource) {
        if (weaponRenderer.renderPerspective != ItemDisplayContext.GUI) {
          return RenderType.eyes(GLOWING_TEXTURE);
        }
        return super.getRenderType(animatable, bufferSource);
      }

      @Override
      public void render(PoseStack poseStack, RedEyesTachiItem animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (weaponRenderer.renderPerspective == ItemDisplayContext.GUI) {
          return;
        }
        super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
      }
    });
  }

  @Override
  public ResourceLocation getTextureLocation(RedEyesTachiItem animatable) {
    if (isHunting) {
      if (renderPerspective == ItemDisplayContext.GUI) {
        return HUNTIOG_TEXTURE;
      }
      return GEO_HUNTIOG_TEXTURE;
    }
    return super.getTextureLocation(animatable);
  }

  public void renderByItem(@Nullable LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
    extracted(livingEntity, displayContext);
    super.renderByItem(itemStack, displayContext, poseStack, bufferSource, combinedLight, combinedOverlay);
  }

  private void extracted(LivingEntity livingEntity, ItemDisplayContext displayContext) {
    this.livingEntity = livingEntity;
    if (displayContext == ItemDisplayContext.GUI) {
      this.livingEntity = Minecraft.getInstance().player;
    }

    if (this.livingEntity != null && this.livingEntity.hasEffect(ModMobEffects.RED_EYES_HUNTING)) {
      isHunting = true;
    }
  }

  @Override
  public void doPostRenderCleanup() {
    super.doPostRenderCleanup();
    isHunting = false;
    livingEntity = null;
  }
}
