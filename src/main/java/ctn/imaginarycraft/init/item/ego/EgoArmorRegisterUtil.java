package ctn.imaginarycraft.init.item.ego;

import com.mojang.datafixers.util.Function5;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.armor.EgoArmorItem;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class EgoArmorRegisterUtil {
  protected static EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul
  ) {
    return registerSuit(id, zhName, lcLevelType, virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, EgoArmorItem::new);
  }

  public static Holder<ArmorMaterial> getArmorMaterialHolder(LcLevelType lcLevelType) {
    return switch (lcLevelType) {
      case ZAYIN -> ModArmorMaterials.ZAYIN;
      case TETH -> ModArmorMaterials.TETH;
      case HE -> ModArmorMaterials.HE;
      case WAW -> ModArmorMaterials.WAW;
      case ALEPH -> ModArmorMaterials.ALEPH;
    };
  }

  protected static EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul
  ) {
    return registerSuit(id, zhName, lcLevelType, material, virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, EgoArmorItem::new);
  }

  protected static <C extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> function
  ) {
    return registerSuit(id, zhName, lcLevelType, material, virtueUsageReqBuilder, builder, properties,
      renderProvider, physics, spirit, erosion, theSoul, function, function, function);
  }

  protected static <C extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> function
  ) {
    return registerSuit(id, zhName, lcLevelType, getArmorMaterialHolder(lcLevelType), virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, function, function, function);
  }

  protected static <C extends EgoArmorItem, L extends EgoArmorItem, B extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> chestplateFunction,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends L> leggingsFunction,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends B> bootsFunction
  ) {
    double[] physicsArray = splitIntoThreeUnequalParts(physics - ModAttributes.PHYSICS_VULNERABLE_DEFAULT_VALUE);
    double[] spiritArray = splitIntoThreeUnequalParts(spirit - ModAttributes.SPIRIT_VULNERABLE_DEFAULT_VALUE);
    double[] erosionArray = splitIntoThreeUnequalParts(erosion - ModAttributes.EROSION_VULNERABLE_DEFAULT_VALUE);
    double[] theSoulArray = splitIntoThreeUnequalParts(theSoul - ModAttributes.THE_SOUL_VULNERABLE_DEFAULT_VALUE);
    return new EgoArmor(
      register(id + "_" + ArmorItem.Type.CHESTPLATE.getName(), zhName, lcLevelType, ArmorItem.Type.CHESTPLATE, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[2], spiritArray[2], erosionArray[2], theSoulArray[2], chestplateFunction),
      register(id + "_" + ArmorItem.Type.LEGGINGS.getName(), zhName, lcLevelType, ArmorItem.Type.LEGGINGS, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[1], spiritArray[1], erosionArray[1], theSoulArray[1], leggingsFunction),
      register(id + "_" + ArmorItem.Type.BOOTS.getName(), zhName, lcLevelType, ArmorItem.Type.BOOTS, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[0], spiritArray[0], erosionArray[0], theSoulArray[0], bootsFunction));
  }

  /**
   * 注册一个EGO护甲物品
   *
   * @param id                    物品的唯一标识符
   * @param zhName                物品的中文名称
   * @param lcLevelType           Lobotomy Corporation中的等级类型（ZAYIN, TETH, HE, WAW, ALEPH）
   * @param builder               EGO护甲构建器
   * @param physics               物理属性值
   * @param spirit                理性属性值
   * @param erosion               侵蚀属性值
   * @param theSoul               灵魂属性值
   * @param virtueUsageReqBuilder 德性使用需求构建器
   * @param item                  用于创建具体EGO护甲物品的函数
   * @return 返回注册后的EGO护甲物品DeferredItem对象
   */
  @NotNull
  protected static <I extends EgoArmorItem> DeferredItem<I> register(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    ArmorItem.Type armorItemType,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends I> item
  ) {
    DeferredItem<I> deferredItem = EgoArmorItems.REGISTRY.register(id, () -> item.apply(material, armorItemType, properties, builder
      .virtueUsageReqBuilder(virtueUsageReqBuilder)
      .vulnerable(physics, spirit, erosion, theSoul), renderProvider));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    switch (armorItemType) {
      case HELMET -> DatagenItemTag.HEAD_ARMOR.add(deferredItem);
      case CHESTPLATE -> DatagenItemTag.CHEST_ARMOR.add(deferredItem);
      case LEGGINGS -> DatagenItemTag.LEG_ARMOR.add(deferredItem);
      case BOOTS -> DatagenItemTag.FOOT_ARMOR.add(deferredItem);
    }
    DatagenItemTag.EGO_ARMOUR.add(deferredItem);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }

  /**
   *
   * @param chest 胸
   * @param legs  腿
   * @param feet  脚
   */
  public record EgoArmor(
    DeferredItem<EgoArmorItem> chest,
    DeferredItem<EgoArmorItem> legs,
    DeferredItem<EgoArmorItem> feet) implements Iterable<DeferredItem<EgoArmorItem>> {
    @Override
    public @NotNull Iterator<DeferredItem<EgoArmorItem>> iterator() {
      return getSet().iterator();
    }

    public @NotNull Set<DeferredItem<EgoArmorItem>> getSet() {
      return Set.of(this.chest, this.legs, this.feet);
    }

    public @NotNull Map<EquipmentSlot, DeferredItem<EgoArmorItem>> getMap() {
      return Map.of(
        EquipmentSlot.CHEST, this.chest,
        EquipmentSlot.LEGS, this.legs,
        EquipmentSlot.FEET, this.feet
      );
    }
  }

  /**
   * 拆分数值为不等的三份（无无限循环小数，优先整数）
   *
   * @param n 待拆分数值（整数/小数均可）
   * @return 三个不等的数（数组顺序：小、中、大）
   */
  protected static double[] splitIntoThreeUnequalParts(double n) {
    double avg = n / 3.0;
    return new double[]{avg - 0.01, avg, avg + 0.01};
  }
}
