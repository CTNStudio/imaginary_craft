package ctn.imaginarycraft.client.model;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.model.GeoModel;


public abstract class BasicGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
  public final ResourceLocation modelPath;
  public final ResourceLocation texturePath;
  public final ResourceLocation animationsPath;

  public BasicGeoModel(ResourceLocation pathName) {
    this(pathName, pathName, pathName);
  }

  public BasicGeoModel(ResourceLocation modelPath, ResourceLocation texturePath, ResourceLocation animationsPath) {
    this.modelPath = modelPath;
    this.texturePath = texturePath;
    this.animationsPath = animationsPath;
  }

  public BasicGeoModel(String name) {
    this.modelPath = modelPath(name);
    this.texturePath = texturePath(name);
    this.animationsPath = animationsPath(name);
  }

  public BasicGeoModel(String modelPath, String textureName, String animationsName) {
    this.modelPath = modelPath(modelPath);
    this.texturePath = texturePath(textureName);
    this.animationsPath = animationsPath(animationsName);
  }

  public static ResourceLocation modelPath(String path) {
    return ImaginaryCraft.modRl("geo/" + path + ".geo.json");
  }

  public static ResourceLocation texturePath(String path) {
    return ImaginaryCraft.modRl("textures/geo/" + path + ".png");
  }

  public static ResourceLocation animationsPath(String path) {
    return ImaginaryCraft.modRl("animations/" + path + ".animation.json");
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return GeckoLibCache.getBakedModels().get(this.modelPath) == null ? getDefaultModelResource() : this.modelPath;
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return texturePath;
  }

  @Override
  public ResourceLocation getAnimationResource(T animatable) {
    return animationsPath;
  }

  @NotNull
  protected ResourceLocation getDefaultModelResource() {
    return modelPath("item/default");
  }
}
