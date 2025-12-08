package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.capability.IRandomDamage;
import ctn.imaginarycraft.capability.entity.IInvincibleTickEntity;
import ctn.imaginarycraft.capability.entity.ILcDamageTypeEntity;
import ctn.imaginarycraft.capability.item.IInvincibleTickItem;
import ctn.imaginarycraft.capability.item.ILcDamageTypeItem;
import ctn.imaginarycraft.capability.item.IUsageReqItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModCapabilitys;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;


/**
 * 注册能力
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryCapability {
  public static final List<ItemLike> ITEM_ZAYIN = List.of(
  );

  public static final List<ItemLike> ITEM_TETH = List.of(
  );

  public static final List<ItemLike> ITEM_HE = List.of(
  );

  public static final List<ItemLike> ITEM_WAW = List.of(
  );

  public static final List<ItemLike> ITEM_ALEPH = List.of(
  );

  public static final List<EntityType<?>> ENTITY_ZAYIN = List.of(

  );

  public static final List<EntityType<?>> ENTITY_TETH = List.of(
    EntityType.CAVE_SPIDER,
    EntityType.SPIDER,
    EntityType.PIGLIN,
    EntityType.PILLAGER,
    EntityType.VEX,
    EntityType.SILVERFISH,
    EntityType.ENDERMITE,
    EntityType.PHANTOM,
    EntityType.MAGMA_CUBE,
    EntityType.HUSK,
    EntityType.CREEPER,
    EntityType.BREEZE,
    EntityType.DROWNED,
    EntityType.ZOMBIFIED_PIGLIN,
    EntityType.ZOMBIE,
    EntityType.STRAY,
    EntityType.SKELETON,
    EntityType.BOGGED,
    EntityType.BLAZE,
    EntityType.SLIME
  );

  public static final List<EntityType<?>> ENTITY_HE = List.of(
    EntityType.WITHER_SKELETON,
    EntityType.WITCH,
    EntityType.VINDICATOR,
    EntityType.EVOKER,
    EntityType.ZOGLIN,
    EntityType.SHULKER,
    EntityType.PIGLIN_BRUTE,
    EntityType.HOGLIN,
    EntityType.GHAST,
    EntityType.ENDERMAN,
    EntityType.GUARDIAN
  );

  public static final List<EntityType<?>> ENTITY_WAW = List.of(
    EntityType.RAVAGER,
    EntityType.ELDER_GUARDIAN,
    EntityType.IRON_GOLEM
  );

  public static final List<EntityType<?>> ENTITY_ALEPH = List.of(
    EntityType.WITHER,
    EntityType.ENDER_DRAGON,
    EntityType.WARDEN
  );

  /**
   * 请在这里注册等级
   */
  @SubscribeEvent
  public static void registrarLevel(RegisterCapabilitiesEvent event) {
    registrarLevel(event, LcLevel.ZAYIN, ITEM_ZAYIN);
    registrarLevel(event, LcLevel.TETH, ITEM_TETH);
    registrarLevel(event, LcLevel.HE, ITEM_HE);
    registrarLevel(event, LcLevel.WAW, ITEM_WAW);
    registrarLevel(event, LcLevel.ALEPH, ITEM_ALEPH);

    registrarEntityLevel(event, LcLevel.ZAYIN, ENTITY_ZAYIN);
    registrarEntityLevel(event, LcLevel.TETH, ENTITY_TETH);
    registrarEntityLevel(event, LcLevel.HE, ENTITY_HE);
    registrarEntityLevel(event, LcLevel.WAW, ENTITY_WAW);
    registrarEntityLevel(event, LcLevel.ALEPH, ENTITY_ALEPH);
//		event.registerBlock(LEVEL_BlOCK, (level, blockPos, blockState, blockEntity, ctx) -> capability);, ***);

//		event.registerBlockEntity(LEVEL_BlOCK, ***, (blockEntity, ctx) -> ***);
  }

  @SubscribeEvent
  public static void registrarItem(RegisterCapabilitiesEvent event) {
    for (Item item : BuiltInRegistries.ITEM) {
      if (item instanceof IRandomDamage capability) {
        event.registerItem(ModCapabilitys.RANDOM_DAMAGE_ITEM, (stack, ctx) -> capability, item);
      }

      if (item instanceof IInvincibleTickItem capability) {
        event.registerItem(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM, (stack, ctx) -> capability, item);
      }

      if (item instanceof ILcDamageTypeItem capability) {
        event.registerItem(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM, (stack, ctx) -> capability, item);
      }

      if (item instanceof IUsageReqItem capability) {
        event.registerItem(ModCapabilitys.USAGE_REQ_ITEM, (stack, ctx) -> capability, item);
      }
    }
  }

  @SubscribeEvent
  public static void registrarEntity(RegisterCapabilitiesEvent event) {
    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
      event.registerEntity(
        ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ENTITY, entityType, (entity, ctx) ->
          entity instanceof IInvincibleTickEntity capability ? capability : null);

      event.registerEntity(
        ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ENTITY, entityType, (entity, ctx) ->
          entity instanceof ILcDamageTypeEntity capability ? capability : null);
    }
  }

  @SubscribeEvent
  public static void registrarBlockEntity(RegisterCapabilitiesEvent event) {
  }

  @SubscribeEvent
  public static void registrarBlock(RegisterCapabilitiesEvent event) {
  }

  public static void registrarLevel(RegisterCapabilitiesEvent event, LcLevel level, List<ItemLike> list) {
    for (ItemLike itemLike : list) {
      event.registerItem(ModCapabilitys.LcLevel.LC_LEVEL_ITEM, (stack, ctx) ->
        (itemLike instanceof ILcLevel ilevel) ? ilevel : () -> level, itemLike);
    }
  }

  public static <E extends Entity> void registrarEntityLevel(RegisterCapabilitiesEvent event, LcLevel level, EntityType<E> entityType) {
    event.registerEntity(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY, entityType, (stack, ctx) ->
      (stack instanceof ILcLevel ilevel) ? ilevel : () -> level);
  }

  public static void registrarEntityLevel(RegisterCapabilitiesEvent event, LcLevel level, List<EntityType<?>> entityTypeList) {
    for (EntityType<?> entityType : entityTypeList) {
      registrarEntityLevel(event, level, entityType);
    }
  }
}
