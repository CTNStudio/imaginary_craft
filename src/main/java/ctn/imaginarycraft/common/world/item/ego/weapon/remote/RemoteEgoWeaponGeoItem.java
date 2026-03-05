package ctn.imaginarycraft.common.world.item.ego.weapon.remote;

import ctn.imaginarycraft.client.model.*;
import ctn.imaginarycraft.client.renderer.providers.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.client.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponGeoItem extends RemoteEgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final GeoModel<RemoteEgoWeaponGeoItem> model;
  private final @Nullable GeoModel<RemoteEgoWeaponGeoItem> guiModel;

  public RemoteEgoWeaponGeoItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder);
    this.model = geoModel;
    this.guiModel = guiModel;
  }

  public RemoteEgoWeaponGeoItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    this(itemProperties, egoWeaponBuilder, new ModGeoItemModel<>(modPath), new GuiItemModel<>(modPath));
  }

  @Override
  public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> rendererConsumer) {
    rendererConsumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
