package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.capability.block.BlockLcLevel;
import ctn.imaginarycraft.core.capability.block.IBlockLcLevel;
import ctn.imaginarycraft.core.capability.entity.EntityLcLevel;
import ctn.imaginarycraft.core.capability.entity.IEntityLcLevel;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.core.capability.item.IItemLcLevel;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import ctn.imaginarycraft.core.capability.item.ItemLcLevel;
import ctn.imaginarycraft.init.ModCapabilitys;
import ctn.imaginarycraft.init.world.entiey.AbnormalitiesEntityTypes;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


/**
 * 注册能力
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class CapabilityRegistry {
  public static final List<ItemLike> ITEM_ZAYIN = new ArrayList<>();
  public static final List<ItemLike> ITEM_TETH = new ArrayList<>();
  public static final List<ItemLike> ITEM_HE = new ArrayList<>();
  public static final List<ItemLike> ITEM_WAW = new ArrayList<>();
  public static final List<ItemLike> ITEM_ALEPH = new ArrayList<>();
  public static final List<Supplier<Block>> BLOCK_ZAYIN = new ArrayList<>();
  public static final List<Supplier<Block>> BLOCK_TETH = new ArrayList<>();
  public static final List<Supplier<Block>> BLOCK_HE = new ArrayList<>();
  public static final List<Supplier<Block>> BLOCK_WAW = new ArrayList<>();
  public static final List<Supplier<Block>> BLOCK_ALEPH = new ArrayList<>();
  public static final List<Supplier<EntityType<?>>> ENTITY_ZAYIN = new ArrayList<>();
  public static final List<Supplier<EntityType<?>>> ENTITY_TETH = new ArrayList<>();
  public static final List<Supplier<EntityType<?>>> ENTITY_HE = new ArrayList<>();
  public static final List<Supplier<EntityType<?>>> ENTITY_WAW = new ArrayList<>();
  public static final List<Supplier<EntityType<?>>> ENTITY_ALEPH = new ArrayList<>();

  static {
    ENTITY_ALEPH.add(() -> EntityType.WITHER);
    ENTITY_ALEPH.add(() -> EntityType.ENDER_DRAGON);
    ENTITY_ALEPH.add(() -> EntityType.WARDEN);

    ENTITY_WAW.add(() -> EntityType.RAVAGER);
    ENTITY_WAW.add(() -> EntityType.ELDER_GUARDIAN);
    ENTITY_WAW.add(() -> EntityType.IRON_GOLEM);

    ENTITY_HE.add(() -> EntityType.WITHER_SKELETON);
    ENTITY_HE.add(() -> EntityType.WITCH);
    ENTITY_HE.add(() -> EntityType.VINDICATOR);
    ENTITY_HE.add(() -> EntityType.EVOKER);
    ENTITY_HE.add(() -> EntityType.ZOGLIN);
    ENTITY_HE.add(() -> EntityType.SHULKER);
    ENTITY_HE.add(() -> EntityType.PIGLIN_BRUTE);
    ENTITY_HE.add(() -> EntityType.HOGLIN);
    ENTITY_HE.add(() -> EntityType.GHAST);
    ENTITY_HE.add(() -> EntityType.ENDERMAN);
    ENTITY_HE.add(() -> EntityType.GUARDIAN);

    ENTITY_TETH.add(() -> EntityType.CAVE_SPIDER);
    ENTITY_TETH.add(() -> EntityType.SPIDER);
    ENTITY_TETH.add(() -> EntityType.PIGLIN);
    ENTITY_TETH.add(() -> EntityType.PILLAGER);
    ENTITY_TETH.add(() -> EntityType.VEX);
    ENTITY_TETH.add(() -> EntityType.SILVERFISH);
    ENTITY_TETH.add(() -> EntityType.ENDERMITE);
    ENTITY_TETH.add(() -> EntityType.PHANTOM);
    ENTITY_TETH.add(() -> EntityType.MAGMA_CUBE);
    ENTITY_TETH.add(() -> EntityType.HUSK);
    ENTITY_TETH.add(() -> EntityType.CREEPER);
    ENTITY_TETH.add(() -> EntityType.BREEZE);
    ENTITY_TETH.add(() -> EntityType.DROWNED);
    ENTITY_TETH.add(() -> EntityType.ZOMBIFIED_PIGLIN);
    ENTITY_TETH.add(() -> EntityType.ZOMBIE);
    ENTITY_TETH.add(() -> EntityType.STRAY);
    ENTITY_TETH.add(() -> EntityType.SKELETON);
    ENTITY_TETH.add(() -> EntityType.BOGGED);
    ENTITY_TETH.add(() -> EntityType.BLAZE);
    ENTITY_TETH.add(() -> EntityType.SLIME);

    ENTITY_HE.add(AbnormalitiesEntityTypes.GRANT_US_LOVE::get);
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void registerHighest(RegisterCapabilitiesEvent event) {
    itemLevel(event, LcLevelType.ZAYIN, ITEM_ZAYIN);
    itemLevel(event, LcLevelType.TETH, ITEM_TETH);
    itemLevel(event, LcLevelType.HE, ITEM_HE);
    itemLevel(event, LcLevelType.WAW, ITEM_WAW);
    itemLevel(event, LcLevelType.ALEPH, ITEM_ALEPH);
    entityLevel(event, LcLevelType.ZAYIN, ENTITY_ZAYIN);
    entityLevel(event, LcLevelType.TETH, ENTITY_TETH);
    entityLevel(event, LcLevelType.HE, ENTITY_HE);
    entityLevel(event, LcLevelType.WAW, ENTITY_WAW);
    entityLevel(event, LcLevelType.ALEPH, ENTITY_ALEPH);
    blockLevel(event, LcLevelType.ZAYIN, BLOCK_ZAYIN);
    blockLevel(event, LcLevelType.TETH, BLOCK_TETH);
    blockLevel(event, LcLevelType.HE, BLOCK_HE);
    blockLevel(event, LcLevelType.WAW, BLOCK_WAW);
    blockLevel(event, LcLevelType.ALEPH, BLOCK_ALEPH);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerLowest(RegisterCapabilitiesEvent event) {
    for (Item item : BuiltInRegistries.ITEM) {
      registerItem(event, item, IItemLcDamageType.class, ModCapabilitys.LC_DAMAGE_TYPE_ITEM);
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

  private static void itemLevel(RegisterCapabilitiesEvent event, LcLevelType lcLevelType, List<ItemLike> list) {
    if (list.isEmpty()) {
      return;
    }
    event.registerItem(ModCapabilitys.LcLevel.LC_LEVEL_ITEM, (stack, ctx) ->
      switch (lcLevelType) {
        case ZAYIN -> ItemLcLevel.ZAYIN;
        case TETH -> ItemLcLevel.TETH;
        case HE -> ItemLcLevel.HE;
        case WAW -> ItemLcLevel.WAW;
        case ALEPH -> ItemLcLevel.ALEPH;
      }, list.toArray(new ItemLike[0])
    );
  }

  private static void blockLevel(RegisterCapabilitiesEvent event, LcLevelType lcLevelType, List<Supplier<Block>> list) {
    if (list.isEmpty()) {
      return;
    }
    event.registerBlock(ModCapabilitys.LcLevel.LC_LEVEL_BLOCK, (level, blockPos, blockState, blockEntity, c) ->
      switch (lcLevelType) {
        case ZAYIN -> BlockLcLevel.ZAYIN;
        case TETH -> BlockLcLevel.TETH;
        case HE -> BlockLcLevel.HE;
        case WAW -> BlockLcLevel.WAW;
        case ALEPH -> BlockLcLevel.ALEPH;
      }, (Block[]) list.stream().map(Supplier::get).toArray()
    );
  }

  private static void entityLevel(RegisterCapabilitiesEvent event, LcLevelType level, List<Supplier<EntityType<?>>> list) {
    if (list.isEmpty()) {
      return;
    }
    for (Supplier<EntityType<?>> entityType : list) {
      event.registerEntity(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY, entityType.get(), (stack, ctx) ->
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
