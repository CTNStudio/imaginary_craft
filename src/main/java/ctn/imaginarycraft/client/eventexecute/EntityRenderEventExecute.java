package ctn.imaginarycraft.client.eventexecute;

import ctn.imaginarycraft.common.item.ego.armor.EgoArmorItem;
import ctn.imaginarycraft.init.item.ego.EgoArmorItems;
import ctn.imaginarycraft.init.item.ego.EgoArmorRegisterUtil;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class EntityRenderEventExecute {
  // 预定义需要隐藏模型部件的护甲映射，作为常量以避免重复创建
  private static final Map<DeferredItem<EgoArmorItem>, EquipmentSlot> HIDDEN_PARTS_ARMOR_MAP = initializeHiddenPartsArmorMap();
  private static Map<Item, EquipmentSlot> itemMap = null;

  /**
   * 初始化需要隐藏模型部件的护甲映射
   *
   * @return 包含需要隐藏模型部件的护甲的映射表
   */
  private static Map<DeferredItem<EgoArmorItem>, EquipmentSlot> initializeHiddenPartsArmorMap() {
    Map<DeferredItem<EgoArmorItem>, EquipmentSlot> map = new HashMap<>();
    add(EgoArmorItems.IN_THE_NAME_OF_LOVE_AND_HATE, map);
    add(EgoArmorItems.MAGIC_BULLET, map);
    return map;
  }

  private static void add(EgoArmorRegisterUtil.EgoArmor inTheNameOfLoveAndHate, Map<DeferredItem<EgoArmorItem>, EquipmentSlot> map) {
    inTheNameOfLoveAndHate.getMap().forEach((slot, item) -> {
      map.put(item, slot);
    });
  }

  /**
   * 根据特定护甲隐藏玩家部分模型
   * 当玩家装备指定的EGO护甲时，隐藏对应的玩家模型部件
   *
   * @param entity 实体对象，代表当前穿戴护甲的生物
   * @param playerModel 玩家模型对象，用于控制模型部件的可见性
   */
  public static void hiddenParts(LivingEntity entity, PlayerModel<?> playerModel) {
    // 如果itemMap为空，则初始化
    if (itemMap == null) {
      itemMap = new HashMap<>();
      HIDDEN_PARTS_ARMOR_MAP.forEach((item, slot) -> {
        itemMap.put(item.get(), slot);
      });
    }

    // 使用集合记录已处理的装备槽位，防止重复处理
    Set<EquipmentSlot> processedSlots = new HashSet<>();

    // 遍历实体的护甲槽位
    for (var itemStack : entity.getArmorSlots()) {
      // 跳过空槽位
      if (itemStack.isEmpty()) {
        continue;
      }

      // 查找当前护甲是否在预定义的映射中
      EquipmentSlot equipmentSlot = itemMap.get(itemStack.getItem());
      if (equipmentSlot == null || processedSlots.contains(equipmentSlot)) {
        continue;
      }
      // 如果找到匹配的护甲且该槽位未处理过，则设置模型可见性
      processedSlots.add(equipmentSlot);
      // 根据槽位设置模型可见性
      switch (equipmentSlot) {
        case LEGS, FEET -> {
          playerModel.leftPants.visible = false;
          playerModel.rightPants.visible = false;
        }
        case CHEST -> {
          playerModel.jacket.visible = false;
          playerModel.leftSleeve.visible = false;
          playerModel.rightSleeve.visible = false;
        }
      }
    }
  }
}
