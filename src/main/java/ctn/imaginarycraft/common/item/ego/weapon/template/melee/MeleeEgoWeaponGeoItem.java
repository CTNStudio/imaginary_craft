package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.client.model.GuiItemModel;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public abstract class MeleeEgoWeaponGeoItem extends MeleeEgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final GeoModel<MeleeEgoWeaponGeoItem> model;
  private final @Nullable GeoModel<MeleeEgoWeaponGeoItem> guiModel;

  public MeleeEgoWeaponGeoItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder);
    this.model = geoModel;
    this.guiModel = guiModel;
  }

  public MeleeEgoWeaponGeoItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    this(itemProperties, egoWeaponBuilder, new ModGeoItemModel<>(modPath), new GuiItemModel<>(modPath));
  }

  @Override
  public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> rendererConsumer) {
    rendererConsumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  @Override
  public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar);

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
