package ctn.imaginarycraft.common.item.ego.weapon;

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
  protected @Nullable GeoModel<GeoEgoWeaponItem> model;
  protected @Nullable GeoModel<GeoEgoWeaponItem> guiModel;

  public GeoEgoWeaponItem(Builder builder) {
    super(builder);
    this.model = builder.model;
    this.guiModel = builder.guiModel;
  }

  public GeoEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  /// 创建GEO模型渲染
  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  /// 创建动画控制器
  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  /// 获取动画实例缓存
  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
