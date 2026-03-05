package ctn.imaginarycraft.common.world.entity.abnormalities;

import ctn.imaginarycraft.client.model.*;
import ctn.imaginarycraft.init.world.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.util.*;

public class TrainingRabbits extends AbnormalitiesEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public TrainingRabbits(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return createAbnormalitiesAttributes()
      .add(ModAttributes.THE_SOUL_VULNERABLE, 1.0)
      .add(ModAttributes.EROSION_VULNERABLE, 1.0);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public static class TrainingRabbitsRenderer extends GeoEntityRenderer<TrainingRabbits> {
    public TrainingRabbitsRenderer(EntityRendererProvider.Context context) {
      super(context, new ModGeoEntityModel<>("training_rabbits"));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TrainingRabbits animatable) {
      return ModGeoEntityModel.getTexturePath("training_rabbits");
    }
  }
}
