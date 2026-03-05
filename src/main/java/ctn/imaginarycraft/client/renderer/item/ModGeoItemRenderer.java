package ctn.imaginarycraft.client.renderer.item;

import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

/**
 * 一个特殊的物品渲染可在GUI和世界中以不同的模型渲染
 */
public class ModGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
  protected final @Nullable GeoModel<T> guiModel;

  public ModGeoItemRenderer(GeoModel<T> model, @Nullable GeoModel<T> guiModel) {
    super(model);
    this.guiModel = guiModel;
    addRenderLayer(new BlockAndItemGeoLayer<>(this));
  }

  @Override
  public GeoModel<T> getGeoModel() {
    return this.guiModel != null && this.renderPerspective == ItemDisplayContext.GUI ? this.guiModel : this.model;
  }
}
