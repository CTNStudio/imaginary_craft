package ctn.imaginarycraft.client.renderer.item;

import ctn.imaginarycraft.common.item.ego.weapon.melee.RedEyesTachiItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

public class RedEyesTachiItemWeaponRenderer extends ModGeoItemRenderer<RedEyesTachiItem> {
  public RedEyesTachiItemWeaponRenderer(GeoModel<RedEyesTachiItem> model) {
    super(model);
  }

  public RedEyesTachiItemWeaponRenderer(GeoModel<RedEyesTachiItem> model, @Nullable GeoModel<RedEyesTachiItem> guiModel) {
    super(model, guiModel);
  }
}
