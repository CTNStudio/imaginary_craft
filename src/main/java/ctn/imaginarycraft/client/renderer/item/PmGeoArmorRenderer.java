package ctn.imaginarycraft.client.renderer.item;

import ctn.imaginarycraft.client.model.ModGeoArmorModel;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * 盔甲渲染
 */
public class PmGeoArmorRenderer<T extends Item & GeoItem> extends GeoArmorRenderer<T> {
  public PmGeoArmorRenderer(ModGeoArmorModel<T> model) {
    super(model);
  }
}
