package ctn.imaginarycraft.client.model;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class GeoCurioModel<T extends GeoAnimatable> extends GeoModel<T> {
  private final String pathName;
  private final ResourceLocation modelResource;
  private final ResourceLocation textureResource;
  private final ResourceLocation animationResource;

  public GeoCurioModel(String pathName) {
    this(pathName, modelPath(pathName), texturePath(pathName), animationsPath(pathName));
  }

  public GeoCurioModel(String pathName, ResourceLocation modelResource, ResourceLocation textureResource, ResourceLocation animationResource) {
    this.pathName = pathName;
    this.modelResource = modelResource;
    this.textureResource = textureResource;
    this.animationResource = animationResource;
  }

  public static ResourceLocation modelPath(String path) {
    return ImaginaryCraft.modRl("geo/curio/" + path + ".geo.json");
  }

  public static ResourceLocation texturePath(String path) {
    return ImaginaryCraft.modRl("textures/geo/curio/" + path + ".png");
  }

  public static ResourceLocation animationsPath(String path) {
    return ImaginaryCraft.modRl("animations/curio/" + path + ".json");
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return modelResource;
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return textureResource;
  }

  @Override
  public ResourceLocation getAnimationResource(T animatable) {
    return animationResource;
  }

  public String getPathName() {
    return pathName;
  }
}
