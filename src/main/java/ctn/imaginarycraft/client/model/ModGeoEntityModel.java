package ctn.imaginarycraft.client.model;

import software.bernie.geckolib.animatable.GeoAnimatable;

public class ModGeoEntityModel<T extends GeoAnimatable> extends BasicGeoModel<T> {
  public ModGeoEntityModel(String name) {
    this(name, name, name);
  }

  public ModGeoEntityModel(String modelPath, String textureName, String animationsName) {
    super("entity/" + modelPath, "entity/" + textureName, "entity/" + animationsName);
  }
}
