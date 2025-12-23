/**
 * ordeals--violet noon
 * 考验--紫罗兰色正午
 * Grant Us Love
 * 请给我们爱
 * 2025/12/22
 * 尘昨喧
 */

package ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.model.ModGeoEntityModel;
import ctn.imaginarycraft.common.entity.abnormalities.AbnormalitiesEntity;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrantUsLove extends AbnormalitiesEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public GrantUsLove(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return createAbnormalitiesAttributes()
      .add(Attributes.MAX_HEALTH, 350.0)
      .add(Attributes.ATTACK_DAMAGE, 1.0)
      .add(Attributes.MOVEMENT_SPEED, 0)
      .add(Attributes.ATTACK_KNOCKBACK, 0)
      .add(Attributes.GRAVITY, 0.20)

      .add(ModAttributes.PHYSICS_VULNERABLE, 0.8)
      .add(ModAttributes.SPIRIT_VULNERABLE, 2.0)
      .add(ModAttributes.EROSION_VULNERABLE, 0.8)
      .add(ModAttributes.THE_SOUL_VULNERABLE, 1.0)
      ;
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this,"controller", 0, this::predicate));
  }

  private PlayState predicate(AnimationState<GrantUsLove> animationState) {
    animationState.getController().setAnimation(RawAnimation.begin().thenPlay("idle"));
    return PlayState.CONTINUE;
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public static class GrantUsLoveRenderer extends GeoEntityRenderer<GrantUsLove> {
    public GrantUsLoveRenderer(EntityRendererProvider.Context context) {
      super(context, new ModGeoEntityModel<>("grant_us_love"));
      this.shadowRadius = 1.5f;
    }

    @Override
    public void render(GrantUsLove entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      float scale = 0.5f;
      poseStack.scale(scale, scale, scale);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrantUsLove animatable) {
      return ModGeoEntityModel.getTexturePath("grant_us_love");
    }
  }
}
