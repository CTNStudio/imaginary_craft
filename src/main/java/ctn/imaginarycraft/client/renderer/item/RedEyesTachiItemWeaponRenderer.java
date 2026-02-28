package ctn.imaginarycraft.client.renderer.item;

import ctn.imaginarycraft.common.item.ego.weapon.melee.RedEyesTachiItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RedEyesTachiItemWeaponRenderer extends ModGeoItemRenderer<RedEyesTachiItem> {

  public static final @NotNull ResourceLocation GLOWING_TEXTURE = ImaginaryCraft.modRl("textures/geo/item/weapon/red_eyes_tachi_shine.png");

  public RedEyesTachiItemWeaponRenderer(GeoModel<RedEyesTachiItem> model, @Nullable GeoModel<RedEyesTachiItem> guiModel) {
    super(model, guiModel);
    addRenderLayer(new AutoGlowingGeoLayer<>(this) {
      @Override
      protected RenderType getRenderType(RedEyesTachiItem animatable, @Nullable MultiBufferSource bufferSource) {
        ResourceLocation texture = RedEyesTachiItemWeaponRenderer.this.guiModel == null || RedEyesTachiItemWeaponRenderer.this.renderPerspective != ItemDisplayContext.GUI ?
          GLOWING_TEXTURE : this.getTextureResource(animatable);
        return RedEyesTachiItemWeaponRenderer.this.getGlowRenderType(animatable, texture);
      }
    });
  }

  protected RenderType getGlowRenderType(RedEyesTachiItem animatable, ResourceLocation texture) {
    return RenderType.eyes(texture);
  }

}
