package ctn.singularity.lib.client.model;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public abstract class BasicGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
  public final ResourceLocation modelPath;
  public final ResourceLocation texturePath;
  public final ResourceLocation animationsPath;

  public BasicGeoModel(ResourceLocation pathName) {
    this(pathName, pathName, pathName);
  }

  public BasicGeoModel(ResourceLocation pathName, ResourceLocation texturePath) {
    this(pathName, texturePath, pathName);
  }

  public BasicGeoModel(ResourceLocation modelPath, ResourceLocation texturePath, ResourceLocation animationsPath) {
    this.modelPath = modelPath;
    this.texturePath = texturePath;
    this.animationsPath = animationsPath;
  }

  @Override
  public ResourceLocation getModelResource(T animatable) {
    return modelPath;
  }

  @Override
  public ResourceLocation getTextureResource(T animatable) {
    return texturePath;
  }

  @Override
  public ResourceLocation getAnimationResource(T animatable) {
    return animationsPath;
  }
}
