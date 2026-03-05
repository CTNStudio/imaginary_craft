package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.renderer.armor.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.client.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

import javax.annotation.*;
import java.util.function.*;

/**
 * 盔甲渲染提供程序
 */
public class ModGeoArmourRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
  protected final GeoModel<T> model;
  private final Function<GeoModel<T>, GeoArmorRenderer<T>> rendererFunction;
  private @Nullable GeoArmorRenderer<T> renderer;

  public ModGeoArmourRenderProvider(GeoModel<T> model, Function<GeoModel<T>, GeoArmorRenderer<T>> rendererFunction) {
    this.model = model;
    this.rendererFunction = rendererFunction;
  }

  public ModGeoArmourRenderProvider(GeoModel<T> model) {
    this.model = model;
    this.rendererFunction = RoughAndFineArmorRenderer::new;
  }

  @Nullable
  @Override
  public <E extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity, ItemStack itemStack,
                                                                       @Nullable EquipmentSlot equipmentSlot,
                                                                       @Nullable HumanoidModel<E> original) {

    if (this.renderer == null) {
      this.renderer = rendererFunction.apply(this.model);
    }
    return this.renderer;
  }
}
