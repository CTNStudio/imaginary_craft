package ctn.imaginarycraft.client.renderer.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * 一个特殊的物品渲染可在GUI和世界中以不同的模型渲染
 */
public class ModGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
  private final @Nullable GeoModel<T> guiModel;

  public ModGeoItemRenderer(GeoModel<T> model, @Nullable GeoModel<T> guiModel) {
    super(model);
    this.guiModel = guiModel;
  }

  public ModGeoItemRenderer(GeoModel<T> model) {
    super(model);
    this.guiModel = null;
  }

  @Override
  public GeoModel<T> getGeoModel() {
    GeoModel<T> guiModel = this.guiModel;
    if (this.renderPerspective == ItemDisplayContext.GUI && guiModel != null) {
      return guiModel;
    }
    return this.model;
  }
}
