package ctn.imaginarycraft.common.world.item.ego.weapon.melee.swords;

import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.client.model.*;
import ctn.imaginarycraft.client.renderer.providers.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.client.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.util.*;

import java.util.function.*;

/**
 * 这玩意就是原版的剑
 */
public class SwordsEgoWeaponGeoItem extends SwordsEgoWeaponItem implements GeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  protected final GeoModel<MeleeEgoWeaponGeoItem> model;
  protected final @Nullable GeoModel<MeleeEgoWeaponGeoItem> guiModel;

  public SwordsEgoWeaponGeoItem(Tier tier, Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(tier, itemProperties, egoWeaponBuilder);
    this.model = geoModel;
    this.guiModel = guiModel;
  }

  public SwordsEgoWeaponGeoItem(Tier tier, Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, String modPath) {
    this(tier, itemProperties, egoWeaponBuilder, new ModGeoItemModel<>(modPath), new GuiItemModel<>(modPath));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> rendererConsumer) {
    rendererConsumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
