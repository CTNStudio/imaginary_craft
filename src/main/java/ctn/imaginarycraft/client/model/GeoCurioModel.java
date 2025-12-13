package ctn.imaginarycraft.client.model;

import software.bernie.geckolib.animatable.GeoAnimatable;

public class GeoCurioModel<T extends GeoAnimatable> extends BasicGeoModel<T> {

  public GeoCurioModel(String pathName) {
    this(pathName, pathName, pathName);
  }

  public GeoCurioModel(String modelPath, String textureName, String animationsName) {
    super("curio/" + modelPath, "curio/" + textureName, "curio/" + animationsName);
  }
}
