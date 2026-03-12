package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.renderer.item.ModGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.BiFunction;

/**
 * 物品渲染提供程序
 */
public class ModGeoItemRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
  private final BiFunction<GeoModel<T>, GeoModel<T>, GeoItemRenderer<T>> rendererFunction;
  protected final GeoModel<T> defaultModel;
  protected final @Nullable GeoModel<T> guiModel;
  private GeoItemRenderer<T> renderer;

  public ModGeoItemRenderProvider(GeoModel<T> defaultModel, @Nullable GeoModel<T> guiModel, BiFunction<GeoModel<T>, GeoModel<T>, GeoItemRenderer<T>> rendererFunction) {
    this.defaultModel = defaultModel;
    this.guiModel = guiModel;
    this.rendererFunction = rendererFunction;
  }

  public ModGeoItemRenderProvider(GeoModel<T> defaultModel, @Nullable GeoModel<T> guiModel) {
    this(defaultModel, guiModel, ModGeoItemRenderer::new);
  }


  @Override
  public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
    if (this.renderer == null) {
      this.renderer = rendererFunction.apply(this.defaultModel, this.guiModel);
    }
    return this.renderer;
  }
}
