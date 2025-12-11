package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.renderer.item.ModGeoItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;

/**
 * 物品渲染提供程序
 */
public class ModGeoItemRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
  protected final GeoModel<T> defaultModel;
  protected final @Nullable GeoModel<T> guiModel;
  private ModGeoItemRenderer<T> renderer;

  public ModGeoItemRenderProvider(GeoModel<T> defaultModel, @Nullable GeoModel<T> guiModel) {
    this.defaultModel = defaultModel;
    this.guiModel = guiModel;
  }

  @Override
  public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
    if (this.renderer == null) {
      this.renderer = new ModGeoItemRenderer<>(this.defaultModel, this.guiModel);
    }

    return this.renderer;
  }
}
