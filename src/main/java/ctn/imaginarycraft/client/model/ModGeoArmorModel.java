package ctn.imaginarycraft.client.model;

import net.minecraft.resources.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;

public class ModGeoArmorModel<T extends GeoAnimatable> extends BasicGeoModel<T> {

  public ModGeoArmorModel(String name) {
    this(name, name, name);
  }

  public ModGeoArmorModel(String modelPath, String textureName, String animationsName) {
    super("armor/" + modelPath, "armor/" + textureName, "armor/" + animationsName);
  }

  @NotNull
  @Override
  protected ResourceLocation getDefaultModelResource() {
    return modelPath("armor/default");
  }
}
