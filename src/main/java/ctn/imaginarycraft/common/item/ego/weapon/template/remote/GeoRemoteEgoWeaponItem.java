package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

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

/**
 * 远程EGO武器
 */
public abstract class GeoRemoteEgoWeaponItem extends RemoteEgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final GeoModel<GeoRemoteEgoWeaponItem> model;
  private final @Nullable GeoModel<GeoRemoteEgoWeaponItem> guiModel;

  public GeoRemoteEgoWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder);
    this.model = model;
    this.guiModel = guiModel;
  }

  public GeoRemoteEgoWeaponItem(Properties properties, Builder builder, String modPath) {
    this(properties, builder, new ModGeoItemModel<>(modPath), new GuiItemModel<>(modPath));
  }

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
