package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.api.capability.block.BlockLcLevel;
import ctn.imaginarycraft.api.capability.block.IBlockLcLevel;
import ctn.imaginarycraft.api.capability.entity.EntityLcLevel;
import ctn.imaginarycraft.api.capability.entity.IEntityLcLevel;
import ctn.imaginarycraft.api.capability.item.*;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModCapabilitys;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;


/**
 * 注册能力
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryCapability {
  public static final List<ItemLike> ITEM_ZAYIN = List.of();
  public static final List<ItemLike> ITEM_TETH = List.of();
  public static final List<ItemLike> ITEM_HE = List.of();
  public static final List<ItemLike> ITEM_WAW = List.of();
  public static final List<ItemLike> ITEM_ALEPH = List.of();
  public static final List<Block> BLOCK_ZAYIN = List.of();
  public static final List<Block> BLOCK_TETH = List.of();
  public static final List<Block> BLOCK_HE = List.of();
  public static final List<Block> BLOCK_WAW = List.of();
  public static final List<Block> BLOCK_ALEPH = List.of();
  public static final List<EntityType<?>> ENTITY_ZAYIN = List.of();
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
    EntityType.SLIME);
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
    EntityType.GUARDIAN);
  public static final List<EntityType<?>> ENTITY_WAW = List.of(
    EntityType.RAVAGER,
    EntityType.ELDER_GUARDIAN,
    EntityType.IRON_GOLEM);
  public static final List<EntityType<?>> ENTITY_ALEPH = List.of(
    EntityType.WITHER,
    EntityType.ENDER_DRAGON,
    EntityType.WARDEN);

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void registerHighest(RegisterCapabilitiesEvent event) {
    itemLevel(event, LcLevel.ZAYIN, ITEM_ZAYIN);
    itemLevel(event, LcLevel.TETH, ITEM_TETH);
    itemLevel(event, LcLevel.HE, ITEM_HE);
    itemLevel(event, LcLevel.WAW, ITEM_WAW);
    itemLevel(event, LcLevel.ALEPH, ITEM_ALEPH);
    entityLevel(event, LcLevel.ZAYIN, ENTITY_ZAYIN);
    entityLevel(event, LcLevel.TETH, ENTITY_TETH);
    entityLevel(event, LcLevel.HE, ENTITY_HE);
    entityLevel(event, LcLevel.WAW, ENTITY_WAW);
    entityLevel(event, LcLevel.ALEPH, ENTITY_ALEPH);
    blockLevel(event, LcLevel.ZAYIN, BLOCK_ZAYIN);
    blockLevel(event, LcLevel.TETH, BLOCK_TETH);
    blockLevel(event, LcLevel.HE, BLOCK_HE);
    blockLevel(event, LcLevel.WAW, BLOCK_WAW);
    blockLevel(event, LcLevel.ALEPH, BLOCK_ALEPH);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerLowest(RegisterCapabilitiesEvent event) {
    for (Item item : BuiltInRegistries.ITEM) {
      registerItem(event, item, IItemRandomDamage.class, ModCapabilitys.RANDOM_DAMAGE_ITEM);
      registerItem(event, item, IItemInvincibleTick.class, ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM);
      registerItem(event, item, IItemLcDamageType.class, ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM);
      registerItem(event, item, IItemUsageReq.class, ModCapabilitys.USAGE_REQ_ITEM);
      registerItem(event, item, IItemLcLevel.class, ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
    }

    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
      registerEntity(event, entityType, IEntityLcLevel.class, ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    }

    for (BlockEntityType<?> blockEntityType : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
      registerBlockEntity(event, blockEntityType, IBlockLcLevel.class, ModCapabilitys.LcLevel.LC_LEVEL_BLOCK);
    }

    for (Block block : BuiltInRegistries.BLOCK) {
      registerBlock(event, block, IBlockLcLevel.class, ModCapabilitys.LcLevel.LC_LEVEL_BLOCK);
    }
  }

  private static <T, C> void registerItem(
    RegisterCapabilitiesEvent event,
    Item item,
    Class<T> capabilityClass,
    ItemCapability<T, C> capability
  ) {
    if (!capabilityClass.isInstance(item) || event.isItemRegistered(capability, item)) {
      return;
    }
    event.registerItem(capability, (stack, ctx) ->
      capabilityClass.cast(item), item);
  }

  private static <T, C> void registerBlock(
    RegisterCapabilitiesEvent event,
    Block block,
    Class<T> capabilityClass,
    BlockCapability<T, C> capability
  ) {
    if (!capabilityClass.isInstance(block) || event.isBlockRegistered(capability, block)) {
      return;
    }
    event.registerBlock(capability, (level, blockPos, blockState, blockEntity, c) ->
      capabilityClass.cast(block), block);
  }

  private static <T, C> void registerBlockEntity(
    RegisterCapabilitiesEvent event,
    BlockEntityType<?> block,
    Class<T> capabilityClass,
    BlockCapability<T, C> capability
  ) {
    event.registerBlockEntity(capability, block, (blockEntity, c) ->
      capabilityClass.isInstance(blockEntity) ? capabilityClass.cast(blockEntity) : null);
  }

  private static <T, C> void registerEntity(
    RegisterCapabilitiesEvent event,
    EntityType<?> entityType,
    Class<T> capabilityClass,
    EntityCapability<T, C> capability
  ) {
    if (event.isEntityRegistered(capability, entityType)) {
      return;
    }
    event.registerEntity(capability, entityType, (entity, ctx) ->
      capabilityClass.isInstance(entity) ? capabilityClass.cast(entity) : null);
  }

  private static void itemLevel(RegisterCapabilitiesEvent event, LcLevel lcLevel, List<ItemLike> list) {
    if (list.isEmpty()) {
      return;
    }
    event.registerItem(ModCapabilitys.LcLevel.LC_LEVEL_ITEM, (stack, ctx) ->
      switch (lcLevel) {
        case ZAYIN -> ItemLcLevel.ZAYIN;
        case TETH -> ItemLcLevel.TETH;
        case HE -> ItemLcLevel.HE;
        case WAW -> ItemLcLevel.WAW;
        case ALEPH -> ItemLcLevel.ALEPH;
      }, list.toArray(new ItemLike[0])
    );
  }

  private static void blockLevel(RegisterCapabilitiesEvent event, LcLevel lcLevel, List<Block> list) {
    if (list.isEmpty()) {
      return;
    }
    event.registerBlock(ModCapabilitys.LcLevel.LC_LEVEL_BLOCK, (level, blockPos, blockState, blockEntity, c) ->
      switch (lcLevel) {
        case ZAYIN -> BlockLcLevel.ZAYIN;
        case TETH -> BlockLcLevel.TETH;
        case HE -> BlockLcLevel.HE;
        case WAW -> BlockLcLevel.WAW;
        case ALEPH -> BlockLcLevel.ALEPH;
      }, list.toArray(new Block[0])
    );
  }

  private static void entityLevel(RegisterCapabilitiesEvent event, LcLevel level, List<EntityType<?>> list) {
    if (list.isEmpty()) {
      return;
    }
    for (EntityType<?> entityType : list) {
      event.registerEntity(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY, entityType, (stack, ctx) ->
        switch (level) {
          case ZAYIN -> EntityLcLevel.ZAYIN;
          case TETH -> EntityLcLevel.TETH;
          case HE -> EntityLcLevel.HE;
          case WAW -> EntityLcLevel.WAW;
          case ALEPH -> EntityLcLevel.ALEPH;
        }
      );
    }
  }
}
