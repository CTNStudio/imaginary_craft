package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

// TODO 目前是复制弩的代码

/**
 * 弩型EGO武器物品类
 */
public class CrossbowEgoWeaponItem extends GeoRemoteEgoWeaponItem {
  private static final float MAX_CHARGE_DURATION = 1.25F;
  public static final int DEFAULT_RANGE = 8;
  /**
   * 当弩装填进度达到20%时设为true
   */
  private boolean startSoundPlayed = false;
  /**
   * 当弩装填进度达到50%时设为true
   */
  private boolean midLoadSoundPlayed = false;
  private static final float START_SOUND_PERCENT = 0.2F;
  private static final float MID_SOUND_PERCENT = 0.5F;
  private static final float ARROW_POWER = 3.15F;
  private static final float FIREWORK_POWER = 1.6F;
  public static final float MOB_ARROW_POWER = 1.6F;
  private static final CrossbowItem.ChargingSounds DEFAULT_SOUNDS = new CrossbowItem.ChargingSounds(
    Optional.of(SoundEvents.CROSSBOW_LOADING_START), Optional.of(SoundEvents.CROSSBOW_LOADING_MIDDLE), Optional.of(SoundEvents.CROSSBOW_LOADING_END)
  );


  /**
   * 构造函数
   *
   * @param itemProperties 物品属性
   * @param egoWeaponBuilder    EGO武器构建器
   * @param geoModel      物品模型
   * @param guiModel   GUI界面模型
   */
  public CrossbowEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  /**
   * 构造函数
   *
   * @param itemProperties 物品属性
   * @param egoWeaponBuilder    EGO武器构建器
   * @param modPath    模组路径
   */
  public CrossbowEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
    return ARROW_OR_FIREWORK;
  }

  @Override
  public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
    return ARROW_ONLY;
  }

  /**
   * 使用物品时调用的方法，处理右键点击行为
   *
   * @param world  游戏世界
   * @param playerEntity 使用物品的玩家
   * @param handUsed   使用物品的手（主手或副手）
   * @return 交互结果
   */
  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player playerEntity, InteractionHand handUsed) {
    ItemStack itemstackInHand = playerEntity.getItemInHand(handUsed);
    ChargedProjectiles chargedprojectiles = itemstackInHand.get(DataComponents.CHARGED_PROJECTILES);
    if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
      this.performShooting(world, playerEntity, handUsed, itemstackInHand, getShootingPower(chargedprojectiles), 1.0F, null);
      return InteractionResultHolder.consume(itemstackInHand);
    }

    if (playerEntity.getProjectile(itemstackInHand).isEmpty()) {
      return InteractionResultHolder.fail(itemstackInHand);
    }

    this.startSoundPlayed = false;
    this.midLoadSoundPlayed = false;
    playerEntity.startUsingItem(handUsed);
    return InteractionResultHolder.consume(itemstackInHand);
  }

  /**
   * 根据装填的弹药类型获取射击力度
   *
   * @param projectile 已装填的弹药
   * @return 射击力度
   */
  @Contract(pure = true)
  private static float getShootingPower(@NotNull ChargedProjectiles projectile) {
    return projectile.contains(Items.FIREWORK_ROCKET) ? FIREWORK_POWER : ARROW_POWER;
  }

  /**
   * 当玩家停止使用物品时调用（松开右键）
   *
   * @param itemStack        物品堆
   * @param world        游戏世界
   * @param entityLiving 使用物品的实体
   * @param timeLeft     剩余时间
   */
  @Override
  public void releaseUsing(ItemStack itemStack, Level world, LivingEntity entityLiving, int timeLeft) {
    int i = this.getUseDuration(itemStack, entityLiving) - timeLeft;
    float f = getPowerForTime(i, itemStack, entityLiving);
    if (!(f >= 1.0F) || isCharged(itemStack) || !tryLoadProjectiles(entityLiving, itemStack)) {
      return;
    }

    this.getChargingSounds(itemStack).end().ifPresent(soundEventHolder -> world.playSound(
      null,
      entityLiving.getX(),
      entityLiving.getY(),
      entityLiving.getZ(),
      soundEventHolder.value(),
      entityLiving.getSoundSource(),
      1.0F,
      1.0F / (world.getRandom().nextFloat() * MID_SOUND_PERCENT + 1.0F) + START_SOUND_PERCENT
    ));
  }

  /**
   * 尝试装填弹药到弩中
   *
   * @param shooter       射击者
   * @param crossbowStack 弩物品堆
   * @return 装填是否成功
   */
  private static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
    List<ItemStack> list = draw(crossbowStack, shooter.getProjectile(crossbowStack), shooter);
    if (list.isEmpty()) {
      return false;
    }

    crossbowStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
    return true;
  }

  /**
   * 检查弩是否已装填弹药
   *
   * @param crossbowStack 弩物品堆
   * @return 是否已装填
   */
  public static boolean isCharged(@NotNull ItemStack crossbowStack) {
    return !crossbowStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).isEmpty();
  }

  /**
   * 发射弹药
   *
   * @param shooterEntity    射击者
   * @param projectileEntity 投射物
   * @param projectileIndex      弹药索引
   * @param projectileVelocity   速度
   * @param projectileInaccuracy 不准确度
   * @param shootingAngle      角度
   * @param targetEntity     目标实体（可为空）
   */
  @Override
  protected void shootProjectile(LivingEntity shooterEntity, Projectile projectileEntity, int projectileIndex, float projectileVelocity,
                                 float projectileInaccuracy, float shootingAngle, @Nullable LivingEntity targetEntity) {
    Vector3f vector3f;
    if (targetEntity != null) {
      double d0 = targetEntity.getX() - shooterEntity.getX();
      double d1 = targetEntity.getZ() - shooterEntity.getZ();
      double d2 = Math.sqrt(d0 * d0 + d1 * d1);
      double d3 = targetEntity.getY(0.3333333333333333) - projectileEntity.getY() + d2 * START_SOUND_PERCENT;
      vector3f = getProjectileShotVector(shooterEntity, new Vec3(d0, d3, d1), shootingAngle);
    } else {
      Vec3 vec3 = shooterEntity.getUpVector(1.0F);
      Quaternionf quaternionf = new Quaternionf().setAngleAxis(shootingAngle * (float) (Math.PI / 180.0), vec3.x, vec3.y, vec3.z);
      Vec3 vec31 = shooterEntity.getViewVector(1.0F);
      vector3f = vec31.toVector3f().rotate(quaternionf);
    }

    projectileEntity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), projectileVelocity, projectileInaccuracy);
    float f = getShotPitch(shooterEntity.getRandom(), projectileIndex);
    shooterEntity.level().playSound(null, shooterEntity.getX(), shooterEntity.getY(), shooterEntity.getZ(), SoundEvents.CROSSBOW_SHOOT, shooterEntity.getSoundSource(), 1.0F, f);
  }

  /**
   * 计算投射物发射向量
   *
   * @param shooter  射击者
   * @param distance 距离向量
   * @param angle    角度
   * @return 发射方向向量
   */
  private static Vector3f getProjectileShotVector(LivingEntity shooter, @NotNull Vec3 distance, float angle) {
    Vector3f vector3f = distance.toVector3f().normalize();
    Vector3f vector3f1 = new Vector3f(vector3f).cross(new Vector3f(0.0F, 1.0F, 0.0F));
    if ((double) vector3f1.lengthSquared() <= 1.0E-7) {
      Vec3 vec3 = shooter.getUpVector(1.0F);
      vector3f1 = new Vector3f(vector3f).cross(vec3.toVector3f());
    }

    Vector3f vector3f2 = new Vector3f(vector3f).rotateAxis((float) (Math.PI / 2), vector3f1.x, vector3f1.y, vector3f1.z);
    return new Vector3f(vector3f).rotateAxis(angle * (float) (Math.PI / 180.0), vector3f2.x, vector3f2.y, vector3f2.z);
  }

  /**
   * 创建投射物实例
   *
   * @param world   游戏世界
   * @param shooterEntity 射击者
   * @param weaponItem  武器物品堆
   * @param ammoItem    弹药物品堆
   * @param isCrit  是否暴击
   * @return 投射物实例
   */
  @Override
  protected @NotNull Projectile createProjectile(@NotNull Level world, @NotNull LivingEntity shooterEntity, @NotNull ItemStack weaponItem, @NotNull ItemStack ammoItem, boolean isCrit) {
    if (ammoItem.is(Items.FIREWORK_ROCKET)) {
      return new FireworkRocketEntity(world, ammoItem, shooterEntity, shooterEntity.getX(), shooterEntity.getEyeY() - 0.15F, shooterEntity.getZ(), true);
    }

    Projectile projectile = super.createProjectile(world, shooterEntity, weaponItem, ammoItem, isCrit);
    if (projectile instanceof AbstractArrow abstractarrow) {
      abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
    }

    return projectile;
  }

  /**
   * 获取使用物品时消耗的耐久度
   *
   * @param stack 物品堆
   * @return 消耗的耐久度
   */
  @Override
  protected int getDurabilityUse(@NotNull ItemStack stack) {
    return stack.is(Items.FIREWORK_ROCKET) ? 3 : 1;
  }

  /**
   * 执行射击操作
   *
   * @param world      游戏世界
   * @param shooterEntity    射击者
   * @param handUsed       使用的手
   * @param weaponItem     武器物品堆
   * @param projectileVelocity   速度
   * @param projectileInaccuracy 不准确度
   * @param targetEntity     目标实体（可为空）
   */
  public void performShooting(Level world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem,
                              float projectileVelocity, float projectileInaccuracy, @Nullable LivingEntity targetEntity) {
    if (!(world instanceof ServerLevel serverLevel) ||
      shooterEntity instanceof Player player && EventHooks.onArrowLoose(weaponItem, shooterEntity.level(), player, 1, true) < 0) {
      return;
    }

    ChargedProjectiles chargedprojectiles = weaponItem.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
    if (chargedprojectiles == null || chargedprojectiles.isEmpty()) {
      return;
    }

    this.shoot(serverLevel, shooterEntity, handUsed, weaponItem, chargedprojectiles.getItems(), projectileVelocity, projectileInaccuracy, shooterEntity instanceof Player, targetEntity);
    if (!(shooterEntity instanceof ServerPlayer serverPlayer)) {
      return;
    }

    CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, weaponItem);
    serverPlayer.awardStat(Stats.ITEM_USED.get(weaponItem.getItem()));
  }

  /**
   * 获取射击音调
   *
   * @param random 随机数生成器
   * @param index  弹药索引
   * @return 音调
   */
  private static float getShotPitch(RandomSource random, int index) {
    return index == 0 ? 1.0F : getRandomShotPitch((index & 1) == 1, random);
  }

  /**
   * 获取随机射击音调
   *
   * @param isHighPitched 是否高音调
   * @param random        随机数生成器
   * @return 音调
   */
  private static float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
    float f = isHighPitched ? 0.63F : 0.43F;
    return 1.0F / (random.nextFloat() * MID_SOUND_PERCENT + 1.8F) + f;
  }

  /**
   * 物品使用过程中每刻调用的方法
   *
   * @param world        游戏世界
   * @param usingEntity 使用物品的实体
   * @param itemStack        物品堆
   * @param count        剩余使用时间
   */
  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int count) {
    if (world.isClientSide) {
      return;
    }

    CrossbowItem.ChargingSounds crossbowItemChargingSounds = this.getChargingSounds(itemStack);
    float f = (float) (itemStack.getUseDuration(usingEntity) - count) / (float) getChargeDuration(itemStack, usingEntity);
    if (f < START_SOUND_PERCENT) {
      this.startSoundPlayed = false;
      this.midLoadSoundPlayed = false;
    }

    if (f >= START_SOUND_PERCENT && !this.startSoundPlayed) {
      this.startSoundPlayed = true;
      crossbowItemChargingSounds.start().ifPresent(soundEventHolder -> world.playSound(
        null, usingEntity.getX(), usingEntity.getY(), usingEntity.getZ(), soundEventHolder.value(), SoundSource.PLAYERS, MID_SOUND_PERCENT, 1.0F
      ));
    }

    if (f >= MID_SOUND_PERCENT && !this.midLoadSoundPlayed) {
      this.midLoadSoundPlayed = true;
      crossbowItemChargingSounds.mid().ifPresent(soundEventHolder -> world.playSound(
        null, usingEntity.getX(), usingEntity.getY(), usingEntity.getZ(), soundEventHolder.value(), SoundSource.PLAYERS, MID_SOUND_PERCENT, 1.0F
      ));
    }
  }

  /**
   * 获取物品使用持续时间
   *
   * @param itemStack  物品堆
   * @param usingEntity 使用物品的实体
   * @return 使用持续时间
   */
  @Override
  public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity usingEntity) {
    return getChargeDuration(itemStack, usingEntity) + 3;
  }

  /**
   * 获取弩装填所需时间
   *
   * @param itemStack   物品堆
   * @param shooterEntity 射击者
   * @return 装填时间（tick）
   */
  public static int getChargeDuration(ItemStack itemStack, LivingEntity shooterEntity) {
    float f = EnchantmentHelper.modifyCrossbowChargingTime(itemStack, shooterEntity, MAX_CHARGE_DURATION);
    return Mth.floor(f * 20.0F);
  }

  /**
   * 获取物品使用时的动画类型
   *
   * @param stack 物品堆
   * @return 动画类型
   */
  @Override
  public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
    return UseAnim.CROSSBOW;
  }

  /**
   * 获取装填音效
   *
   * @param itemStack 物品堆
   * @return 装填音效配置
   */
  CrossbowItem.ChargingSounds getChargingSounds(ItemStack itemStack) {
    return EnchantmentHelper.pickHighestLevel(itemStack, EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS).orElse(DEFAULT_SOUNDS);
  }

  /**
   * 根据使用时间计算装填力度
   *
   * @param timeLeft 剩余时间
   * @param itemStack    物品堆
   * @param shooterEntity  射击者
   * @return 装填力度（0.0-1.0）
   */
  private static float getPowerForTime(int timeLeft, ItemStack itemStack, LivingEntity shooterEntity) {
    float f = (float) timeLeft / (float) getChargeDuration(itemStack, shooterEntity);
    if (f > 1.0F) {
      f = 1.0F;
    }

    return f;
  }

  /**
   * 添加物品悬停文本（提示信息）
   *
   * @param itemStack             物品堆
   * @param tooltipContext           提示上下文
   * @param tooltipComponents 提示组件列表
   * @param tooltipFlag       提示标志
   */
  @Override
  public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    ChargedProjectiles chargedprojectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
    if (chargedprojectiles == null || chargedprojectiles.isEmpty()) {
      return;
    }

    ItemStack itemstack = chargedprojectiles.getItems().get(0);
    tooltipComponents
      .add(Component.translatable("item.minecraft.crossbow.projectile")
        .append(CommonComponents.SPACE)
        .append(itemstack.getDisplayName()));
    if (!tooltipFlag.isAdvanced() || !itemstack.is(Items.FIREWORK_ROCKET)) {
      return;
    }

    List<Component> list = new ArrayList<>();
    Items.FIREWORK_ROCKET.appendHoverText(itemstack, tooltipContext, list, tooltipFlag);
    if (list.isEmpty()) {
      return;
    }

    list.replaceAll(sibling -> Component.literal("  ").append(sibling).withStyle(ChatFormatting.GRAY));

    tooltipComponents.addAll(list);
  }

  /**
   * 判断物品是否在释放时使用
   *
   * @param stack 物品堆
   * @return 是否在释放时使用
   */
  @Override
  public boolean useOnRelease(ItemStack stack) {
    return stack.is(this);
  }

  /**
   * 获取默认弹射范围
   *
   * @return 默认范围
   */
  @Override
  public int getDefaultProjectileRange() {
    return DEFAULT_RANGE;
  }
}
