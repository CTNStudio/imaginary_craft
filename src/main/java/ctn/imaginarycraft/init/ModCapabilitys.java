package ctn.imaginarycraft.init;

import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.capability.IRandomDamage;
import ctn.imaginarycraft.capability.block.ILevelBlock;
import ctn.imaginarycraft.capability.entity.IInvincibleTickEntity;
import ctn.imaginarycraft.capability.entity.ILcDamageTypeEntity;
import ctn.imaginarycraft.capability.item.IInvincibleTickItem;
import ctn.imaginarycraft.capability.item.ILcDamageTypeItem;
import ctn.imaginarycraft.capability.item.IUsageReqItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.mixin.DamageSourceMixin;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public final class ModCapabilitys {
  /**
   * 随机伤害
   */
  public static final ItemCapability<IRandomDamage, Void> RANDOM_DAMAGE_ITEM =
    ItemCapability.createVoid(ImaginaryCraft.modRl("random_damage"), IRandomDamage.class);

  /**
   * 物品使用条件
   */
  public static final ItemCapability<IUsageReqItem, Void> USAGE_REQ_ITEM =
    ItemCapability.createVoid(ImaginaryCraft.modRl("usage_req_item"), IUsageReqItem.class);

  /**
   * 等级
   */
  public static class LcLevel {
    public static final ItemCapability<ILcLevel, Void> LC_LEVEL_ITEM =
      ItemCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_level_item"), ILcLevel.class);

    public static final EntityCapability<ILcLevel, Void> LC_LEVEL_ENTITY =
      EntityCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_level_entity"), ILcLevel.class);

    public static final BlockCapability<ILevelBlock, Void> LC_LEVEL_BLOCK =
      BlockCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_level_block"), ILevelBlock.class);
  }

  /**
   * 也可以通过mixin类 {@link DamageSourceMixin} 的 {@link IDamageSource}接口方法代替或覆盖这些
   */
  public static class InvincibleTick {
    public static final ItemCapability<IInvincibleTickItem, Void> INVINCIBLE_TICK_ITEM =
      ItemCapability.createVoid(ImaginaryCraft.modRl("invincible_tick_item"), IInvincibleTickItem.class);

    /**
     * 注意：此能力是不被推荐的，因为实体的伤害方法是多种多样的应该使用<p>
     * mixin类 {@link DamageSourceMixin} 的 {@link IDamageSource}接口方法代替，<p>
     * 或者你想给关于这个实体的所有伤害都是一个值，那可以使用此能力
     * <p>
     * 应用场景：让玩家造成的伤害都没有无敌帧，弹幕
     * <p>
     * 注：基于原版的伤害系统
     */
    public static final EntityCapability<IInvincibleTickEntity, Void> INVINCIBLE_TICK_ENTITY =
      EntityCapability.createVoid(ImaginaryCraft.modRl("invincible_tick_entity"), IInvincibleTickEntity.class);
  }

  /**
   * 脑叶伤害
   */
  public static class LcDamageType {
    public static final ItemCapability<ILcDamageTypeItem, Void> LC_DAMAGE_TYPE_ITEM =
      ItemCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_damage_type_item"), ILcDamageTypeItem.class);

    public static final EntityCapability<ILcDamageTypeEntity, Void> LC_DAMAGE_TYPE_ENTITY =
      EntityCapability.createVoid(ImaginaryCraft.modRl("lobotomy_corporation_damage_type_entity"), ILcDamageTypeEntity.class);
  }
}
