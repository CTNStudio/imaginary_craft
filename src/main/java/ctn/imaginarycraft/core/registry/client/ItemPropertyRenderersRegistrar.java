package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.init.world.item.ToolItems;
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
public final class ItemPropertyRenderersRegistrar {
  public static final ResourceLocation MODE_BOOLEAN = ImaginaryCraft.modRl("current_lobotomy.mode_boolean");
  public static final ResourceLocation CURRENT_LC_DAMAGE_TYPE = ImaginaryCraft.modRl("current_lobotomy.corporation_damage_type");
//  public static final ResourceLocation RED_EYES_TACHI = ImaginaryCraft.modRl("current_lobotomy.red_eyes_tachi");

  public static final ClampedItemPropertyFunction PROPERTY_MODE_BOOLEAN = (itemStack, clientLevel, livingEntity, i) ->
    Boolean.TRUE == itemStack.get(ModDataComponents.MODE_BOOLEAN) ? 1 : 0;

  /**
   * 注册物品渲染附加
   */
  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      createProperties(ToolItems.CREATIVE_RATIONALITY_TOOL.asItem(), MODE_BOOLEAN, PROPERTY_MODE_BOOLEAN);
//      createProperties(EgoWeaponItems.RED_EYES_TACHI.asItem(), RED_EYES_TACHI, (itemStack, clientLevel, livingEntity, i) -> {
//        if (livingEntity != null && livingEntity.hasEffect(ModMobEffects.RED_EYES_HUNTING)) {
//          return 1;
//        }
//        return 0;
//      });
      createProperties(ToolItems.CHAOS_SWORD.asItem(), CURRENT_LC_DAMAGE_TYPE, (itemStack, clientLevel, livingEntity, i) -> {
        LcDamageType.Component t = itemStack.get(ModDataComponents.LC_DAMAGE_TYPE);
        return t == null ? 0 : switch (t.lcDamageType()) {
          case PHYSICS -> 0;
          case SPIRIT -> 0.1F;
          case EROSION -> 0.2F;
          case THE_SOUL -> 0.3F;
          case null -> 0;
        };
      });
    });
  }

  private static void createProperties(Item item, ResourceLocation propertiesName, ClampedItemPropertyFunction propertyFunction) {
    ItemProperties.register(item, propertiesName, propertyFunction);
  }
}
