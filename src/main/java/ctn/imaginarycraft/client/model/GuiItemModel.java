package ctn.imaginarycraft.client.model;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class GuiItemModel<T extends GeoAnimatable> extends ModGeoItemModel<T> {
  public GuiItemModel(String path) {
    super(path);
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return modelPath("gui_item_model");
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return ImaginaryCraft.modRl("textures/item/" + path + ".png");
  }

  @Override
  public ResourceLocation getAnimationResource(T animatable) {
    return super.getAnimationResource(animatable);
  }
}
