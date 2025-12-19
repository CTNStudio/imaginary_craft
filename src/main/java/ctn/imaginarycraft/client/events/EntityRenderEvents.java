package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.common.item.ego.armor.EgoArmorItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoArmorItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class EntityRenderEvents {
  @SubscribeEvent
  public static void onRenderPlayerEventPre(RenderPlayerEvent.Pre event) {
    Player entity = event.getEntity();
    EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
    hiddenParts(entity, ((PlayerRenderer) renderer).getModel());
  }

  public static void hiddenParts(LivingEntity entity, PlayerModel<?> model) {
    var entrySet = getHiddenPartsEntrySet();
    HashSet<EquipmentSlot> equipmentSlots = new HashSet<>();
    for (ItemStack itemStack : entity.getArmorSlots()) {
      if (itemStack.isEmpty()) {
        continue;
      }
      Item item = itemStack.getItem();
      for (var set : entrySet) {
        EquipmentSlot equipmentSlot = set.getKey();
        if (equipmentSlots.contains(equipmentSlot) || !item.equals(set.getValue().get())) {
          continue;
        }
        equipmentSlots.add(equipmentSlot);
        hiddenParts(equipmentSlot, model);
        break;
      }
    }
  }

  private static @NotNull Set<Map.Entry<EquipmentSlot, DeferredItem<EgoArmorItem>>> getHiddenPartsEntrySet() {
    // TODO 目前没有扩展能力需要后期重构
    return EgoArmorItems.MAGIC_BULLET.getMap().entrySet();
  }

  public static void hiddenParts(EquipmentSlot key, PlayerModel<?> playerModel) {
    switch (key) {
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
