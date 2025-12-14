package ctn.imaginarycraft.common.item.ego.weapon.template;

import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

/**
 * EGO武器
 */
public class GeoEgoWeaponItem extends EgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  protected final GeoModel<GeoEgoWeaponItem> model;
  protected final @Nullable GeoModel<GeoEgoWeaponItem> guiModel;

  public GeoEgoWeaponItem(Builder builder) {
    this(builder.buildProperties(), builder);
  }

  public GeoEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
    this.model = builder.getModel();
    this.guiModel = builder.getGuiModel();
  }

  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
