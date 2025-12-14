package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import ctn.imaginarycraft.common.item.ego.weapon.template.GeoEgoWeaponItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public abstract class GeoMeleeEgoWeaponItem extends MeleeEgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final GeoModel<GeoEgoWeaponItem> model;
  private final @Nullable GeoModel<GeoEgoWeaponItem> guiModel;

  public GeoMeleeEgoWeaponItem(Builder builder) {
    this(builder.buildProperties(), builder);
  }

  public GeoMeleeEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
    this.model = builder.getModel();
    this.guiModel = builder.getGuiModel();
  }

  @Override
  public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  @Override
  public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllers);

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
