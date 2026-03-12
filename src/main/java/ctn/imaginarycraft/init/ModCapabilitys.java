package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.capability.block.IBlockLcLevel;
import ctn.imaginarycraft.core.capability.entity.IEntityLcLevel;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.core.capability.item.IItemLcLevel;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class ModCapabilitys {
  /**
   * 物品使用条件
   */
  public static final ItemCapability<IItemUsageReq, Void> USAGE_REQ_ITEM =
    ItemCapability.createVoid(ImaginaryCraft.modRl("usage_req_item"), IItemUsageReq.class);

  public static final ItemCapability<IItemLcDamageType, Void> LC_DAMAGE_TYPE_ITEM =
    ItemCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_damage_type_item"), IItemLcDamageType.class);

  /**
   * 等级
   */
  public static class LcLevel {
    public static final ItemCapability<IItemLcLevel, Void> LC_LEVEL_ITEM =
      ItemCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_level_item"), IItemLcLevel.class);

    public static final BlockCapability<IBlockLcLevel, Void> LC_LEVEL_BLOCK =
      BlockCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_block_block"), IBlockLcLevel.class);

    public static final EntityCapability<IEntityLcLevel, Void> LC_LEVEL_ENTITY =
      EntityCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_level_entity"), IEntityLcLevel.class);
  }
}
