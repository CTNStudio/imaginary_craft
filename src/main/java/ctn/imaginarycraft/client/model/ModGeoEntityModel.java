package ctn.imaginarycraft.client.model;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class ModGeoEntityModel<T extends GeoAnimatable> extends GeoModel<T> {
  protected final String path;

  public ModGeoEntityModel(String path) {
    this.path = path;
  }

  public static ResourceLocation fromNamespaceAndPath(String path) {
    return ImaginaryCraft.modRl(path);
  }

  public static ResourceLocation modelPath(String path) {
    return ImaginaryCraft.modRl("geo/entity/" + path + ".geo.json");
  }

  public static ResourceLocation texturePath(String path) {
    return ImaginaryCraft.modRl("textures/geo/entity/" + path + ".png");
  }

  public static ResourceLocation animationsPath(String path) {
    return ImaginaryCraft.modRl("animations/entity/" + path + ".json");
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return modelPath(path);
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return texturePath(path);
  }

  @Override
  public ResourceLocation getAnimationResource(T animatable) {
    return animationsPath(path);
  }
}
