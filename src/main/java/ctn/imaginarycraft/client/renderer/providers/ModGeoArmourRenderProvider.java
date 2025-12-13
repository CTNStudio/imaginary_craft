package ctn.imaginarycraft.client.renderer.providers;

import ctn.imaginarycraft.client.model.ModGeoArmorModel;
import ctn.imaginarycraft.client.renderer.armor.RoughAndSlimArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import javax.annotation.Nullable;

/**
 * 盔甲渲染提供程序
 */
public class ModGeoArmourRenderProvider<T extends Item & GeoItem> implements GeoRenderProvider {
  protected final ModGeoArmorModel<T> model;
  private @Nullable RoughAndSlimArmorRenderer<T> renderer;

  public ModGeoArmourRenderProvider(ModGeoArmorModel<T> model, @Nullable RoughAndSlimArmorRenderer<T> renderer) {
    this.model = model;
    this.renderer = renderer;
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
