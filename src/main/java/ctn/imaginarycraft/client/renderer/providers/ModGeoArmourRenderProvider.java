package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.renderer.armor.RoughAndSlimArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.annotation.Nullable;

/**
 * 盔甲渲染提供程序
 */
public class ModGeoArmourRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
  protected final GeoModel<T> model;
  private @Nullable GeoArmorRenderer<T> renderer;

  public ModGeoArmourRenderProvider(GeoModel<T> model, @Nullable GeoArmorRenderer<T> renderer) {
    this.model = model;
    this.renderer = renderer;
  }

  public ModGeoArmourRenderProvider(GeoModel<T> model) {
    this.model = model;
    this.renderer = null;
  }

  @Nullable
  @Override
  public <E extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity, ItemStack itemStack,
                                                                       @Nullable EquipmentSlot equipmentSlot,
                                                                       @Nullable HumanoidModel<E> original) {
    if (this.renderer == null) {
      this.renderer = new RoughAndSlimArmorRenderer<>(this.model);
    }
    return this.renderer;
  }
}
