package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends ProjectileWeaponItem implements IItemEgo, IItemLcDamageType, IItemUsageReq, IItemInvincibleTick {
  protected final float attackDistance;
  protected final @Nullable LcDamageType lcDamageType;
  protected final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  protected final int invincibleTick;
  protected final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public RemoteEgoWeaponItem(@NotNull Properties properties, @NotNull Builder builder) {
    super(properties.stacksTo(1)
      .attributes(builder.getItemAttributeModifiers())
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, builder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.lcDamageType = builder.lcDamageType;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
    this.invincibleTick = builder.invincibleTick;
    this.attackDistance = builder.attackDistance;
    this.createProjectile = builder.createProjectile;
  }

  /**
   * 远程攻击距离
   */
  public float getAttackDistance() {
    return attackDistance;
  }

  @Override
  protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                       List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit,
                       @Nullable LivingEntity target) {
    super.shoot(level, shooter, hand, weapon, projectileItems, velocity, inaccuracy, isCrit, target);
  }

  protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                       float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
    Projectile projectile = this.createProjectile(level, shooter, weapon, null, isCrit);
    this.shootProjectile(shooter, projectile, 0, velocity, inaccuracy, 0, target);
    level.addFreshEntity(projectile);
    weapon.hurtAndBreak(1, shooter, LivingEntity.getSlotForHand(hand));
  }

  /**
   * 获取物品当前的伤害颜色
   */
  @Override
  @Nullable
  public LcDamageType getLcDamageColorDamageType(ItemStack stack) {
    return lcDamageType;
  }

  @Override
  @Nullable
  public Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return canCauseLcDamageTypes;
  }

  /**
   * 获取武器攻击时造成的无敌帧
   */
  @Override
  public int getInvincibleTick(ItemStack stack) {
    return invincibleTick;
  }

  /**
   * 是否可以挖掘方块
   */
  @Override
  public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
    return !player.isCreative();
  }

  @Override
  public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
    return 72000;
  }

  @Override
  public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
    return UseAnim.NONE;
  }

  @Override
  protected @NotNull Projectile createProjectile(@NotNull Level level, @NotNull LivingEntity shooter, @NotNull ItemStack weapon,
                                                 @Nullable ItemStack ammo, boolean isCrit) {
    return this.createProjectile != null ?
      this.createProjectile.createProjectile(level, shooter, weapon, ammo) :
      super.createProjectile(level, shooter, weapon, Items.ARROW.getDefaultInstance(), isCrit);
  }

  @Override
  public int getDefaultProjectileRange() {
    return (int) getAttackDistance();
  }

  @Override
  public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles(@NotNull ItemStack stack) {
    return (stack1) -> false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
    return (stack1) -> false;
  }

  @Override
  public @NotNull Predicate<ItemStack> getAllSupportedProjectiles(@NotNull ItemStack stack) {
    return (stack1) -> false;
  }

  @Override
  public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
    return (stack1) -> false;
  }

  // TODO 如果是使用箭的武器如果没有箭就消耗一定量的理智（生成特殊箭），正常发射（有箭）消耗少量理智
  @Override
  public @NotNull ItemStack getDefaultCreativeAmmo(@Nullable Player player, @NotNull ItemStack projectileWeaponItem) {
    return Items.AIR.getDefaultInstance();
  }

  public static class Builder extends EgoWeaponItem.Builder<Builder> {
    /**
     * 远程攻击间隔
     */
    public float attackInterval;
    /**
     * 远程攻击距离
     */
    public float attackDistance;

    public CreateProjectile<? extends Projectile> createProjectile;

    public Builder createProjectile(CreateProjectile<? extends Projectile> createProjectile) {
      this.createProjectile = createProjectile;
      return this;
    }

    /**
     * 远程攻击间隔
     */
    public Builder attackInterval(float attackInterval) {
      this.attackInterval = attackInterval;
      return this;
    }

    /**
     * 远程攻击精准度
     */
    public Builder attackDistance(float attackDistance) {
      this.attackDistance = attackDistance;
      return this;
    }

    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackInterval, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return builder.build();
    }
  }

  @FunctionalInterface
  public interface CreateProjectile<T extends Projectile> {
    T createProjectile(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon);
  }
}
