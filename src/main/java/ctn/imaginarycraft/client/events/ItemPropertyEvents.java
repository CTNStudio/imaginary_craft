package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.init.item.ToolItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 物品渲染附加
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public class ItemPropertyEvents {
  public static final ResourceLocation MODE_BOOLEAN = ImaginaryCraft.modRl("mode_boolean");
  public static final ResourceLocation CURRENT_LC_DAMAGE_TYPE = ImaginaryCraft.modRl("current_lobotomy_corporation_damage_type");

  public static final ClampedItemPropertyFunction PROPERTY_MODE_BOOLEAN = (itemStack, clientLevel, livingEntity, i) ->
    Boolean.TRUE == itemStack.get(ModDataComponents.MODE_BOOLEAN) ? 1 : 0;

  public static final ClampedItemPropertyFunction PROPERTY_CURRENT_DAMAGE_TYPE = (itemStack, clientLevel, livingEntity, i) ->
    switch (itemStack.get(ModDataComponents.LC_DAMAGE_TYPE)) {
      case PHYSICS -> 0;
      case SPIRIT -> 0.1F;
      case EROSION -> 0.2F;
      case THE_SOUL -> 0.3F;
      case null -> 0;
    };

  /**
   * 注册物品渲染附加
   */
  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    createProperties(event, ToolItems.CREATIVE_RATIONALITY_TOOL.asItem(), MODE_BOOLEAN, PROPERTY_MODE_BOOLEAN);
    createProperties(event, ToolItems.CHAOS_SWORD.asItem(), CURRENT_LC_DAMAGE_TYPE, PROPERTY_CURRENT_DAMAGE_TYPE);
  }

  private static void createProperties(FMLClientSetupEvent event, Item item, ResourceLocation propertiesName, ClampedItemPropertyFunction propertyFunction) {
    event.enqueueWork(() -> ItemProperties.register(item, propertiesName, propertyFunction));
  }
}
