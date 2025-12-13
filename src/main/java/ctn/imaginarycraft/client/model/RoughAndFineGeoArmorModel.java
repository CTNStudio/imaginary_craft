package ctn.imaginarycraft.client.model;

import software.bernie.geckolib.animatable.GeoAnimatable;

public class RoughAndFineGeoArmorModel<T extends GeoAnimatable> extends ModGeoArmorModel<T> {
  public RoughAndFineGeoArmorModel(String name) {
    super(name);
  }

  public RoughAndFineGeoArmorModel(String modelPath, String textureName, String animationsName) {
    super(modelPath, textureName, animationsName);
  }

}
