package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.renderer.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.client.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

import java.util.function.*;

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
