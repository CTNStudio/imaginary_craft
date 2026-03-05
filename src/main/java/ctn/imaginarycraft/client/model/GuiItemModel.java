package ctn.imaginarycraft.client.model;

import ctn.imaginarycraft.core.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.animatable.*;

public class GuiItemModel<T extends GeoAnimatable> extends ModGeoItemModel<T> {
  private static final ResourceLocation MODELLED_PATH = modelPath("item/gui_item_model");
  private final ResourceLocation resourceLocation;

  public GuiItemModel(String path) {
    super(path);
    this.resourceLocation = ImaginaryCraft.modRl("textures/item/" + path + ".png");
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return MODELLED_PATH;
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return resourceLocation;
  }
}
