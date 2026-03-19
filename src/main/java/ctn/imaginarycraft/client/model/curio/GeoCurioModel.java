package ctn.imaginarycraft.client.model.curio;

import ctn.imaginarycraft.client.model.BasicGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class GeoCurioModel<T extends GeoAnimatable> extends BasicGeoModel<T> {

  public GeoCurioModel(String pathName) {
    this(pathName, pathName, pathName);
  }

  public GeoCurioModel(String modelPath, String textureName, String animationsName) {
    super("curio/" + modelPath, "curio/" + textureName, "curio/" + animationsName);
  }
}
