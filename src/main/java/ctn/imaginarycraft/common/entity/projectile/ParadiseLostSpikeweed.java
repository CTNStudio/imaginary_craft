package ctn.imaginarycraft.common.entity.projectile;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.model.ModGeoEntityModel;
import ctn.imaginarycraft.init.ModDamageTypes;
import ctn.imaginarycraft.init.entiey.AbnormalitiesEntityTypes;
import ctn.imaginarycraft.mixed.IDamageSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

/**
 * 失乐园尖刺
 */
public class ParadiseLostSpikeweed extends Entity implements TraceableEntity, GeoEntity {
  private final AnimatableInstanceCache anims = GeckoLibUtil.createInstanceCache(this);
  private final int lifeTicks = 22;
  @Nullable
  private LivingEntity owner;
  @Nullable
  private UUID ownerUUID;
  /**
   * 伤害
   */
  private float damage = 28;
  private float damage1 = 23;
  private float damage2 = 20;
  private boolean clientSideAttackStarted;
  private int targetNumber = 1;
  private boolean isAttack = false;

  private LivingEntity targetEntity;

  public ParadiseLostSpikeweed(EntityType<?> entityType, Level level) {
    super(entityType, level);
  }

  public static @NotNull ParadiseLostSpikeweed create(Level level, double x, double y, double z, int targetNumber, LivingEntity owner) {
    ParadiseLostSpikeweed entity = new ParadiseLostSpikeweed(AbnormalitiesEntityTypes.PARADISE_LOST_SPIKEWEED.get(), level);
    entity.targetNumber = targetNumber == 0 ? 1 : targetNumber;
    entity.setPos(x, y, z);
    entity.setOwner(owner);
    return entity;
  }

  public static @NotNull ParadiseLostSpikeweed create(Level level, double x, double y, double z, int targetNumber, LivingEntity owner, LivingEntity targetEntity) {
    ParadiseLostSpikeweed entity = create(level, x, y, z, targetNumber, owner);
    entity.targetEntity = targetEntity;
    return entity;
  }

  public static @NotNull ParadiseLostSpikeweed create(Level level, Vec3 vec3, int targetNumber, LivingEntity owner) {
    ParadiseLostSpikeweed entity = new ParadiseLostSpikeweed(AbnormalitiesEntityTypes.PARADISE_LOST_SPIKEWEED.get(), level);
    entity.targetNumber = targetNumber == 0 ? 1 : targetNumber;
    entity.setPos(vec3);
    entity.setOwner(owner);
    return entity;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {

  }

  @Override
  public void tick() {
    super.tick();
    if (level().isClientSide) {
      // TODO 等资源重置 之后写入动画
      return;
    }
    extracted();

    if (tickCount > 60) {
      remove(RemovalReason.DISCARDED);
    }
  }

  private void extracted() {
    if (isAttack || tickCount >= 2) {
      return;
    }

    Level level = level();
    List<Entity> entityList = level.getEntitiesOfClass(Entity.class, getBoundingBox(), this::targetJudgment);
    int i = entityList.size();
    if (i == 0) {
      return;
    }
    for (int j = 0; j < i; j++) {
      Entity livingEntity;
      if (targetEntity != null && targetEntity.isAlive() && targetEntity.isAttackable()) {
        livingEntity = targetEntity;
      } else {
        livingEntity = entityList.get(level.getRandom().nextInt(i));
      }
      if (!dealDamageTo(livingEntity)) {
        continue;
      }
      hit(livingEntity);
      isAttack = true;
      break;
    }
  }

  public void hit(Entity entity) {
    if (entity instanceof LivingEntity livingEntity) {
      livingEntity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 10, 3));
    }
    LivingEntity livingentity = getOwner();
    if (livingentity == null) {
      return;
    }
    int value = livingentity.getRandom().nextInt(2, 4 + 1);
    livingentity.heal(value);
    if (livingentity instanceof Player player) {
      RationalityUtil.modifyValue(player, value, true);
    }
  }

  private boolean dealDamageTo(@NotNull Entity target) {
    LivingEntity livingentity = getOwner();
    float damage = getDamage();

    final ResourceKey<DamageType> theSoul = ModDamageTypes.THE_SOUL;
    if (livingentity == null && !(target.isAlive() && !target.isInvulnerable() && target.getUUID().equals(livingentity.getUUID()))) {
      return target.hurt(getDamageSource(theSoul, livingentity), damage);
    }

    return !livingentity.isAlliedTo(target) && target.hurt(getDamageSource(theSoul, livingentity), damage);
  }

  private @NotNull DamageSource getDamageSource(ResourceKey<DamageType> THE_SOUL, LivingEntity livingentity) {
    DamageSource source = damageSources().source(THE_SOUL, this, livingentity);
    IDamageSource damageSource = (IDamageSource) source;
    damageSource.setImaginaryCraft$LcDamageType(LcDamageType.THE_SOUL);
    damageSource.setImaginaryCraft$DamageLevel(LcLevelType.ALEPH);
    damageSource.setImaginaryCraft$InvincibleTick(10);
    damageSource.setImaginaryCraft$InvincibleTick(10);
    return source;
  }

  @Override
  @Nullable
  public ItemStack getWeaponItem() {
    return owner != null ? this.owner.getMainHandItem() : null;
  }

  @Override
  protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
    if (compound.hasUUID("Owner")) {
      this.ownerUUID = compound.getUUID("Owner");
    }
    if (compound.contains("Damage")) {
      this.damage = compound.getFloat("Damage");
    }
    if (compound.contains("Damage1")) {
      this.damage1 = compound.getFloat("Damage1");
    }
    if (compound.contains("Damage2")) {
      this.damage2 = compound.getFloat("Damage2");
    }
  }

  @Override
  protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
    if (this.ownerUUID != null) {
      compound.putUUID("Owner", this.ownerUUID);
    }
    compound.putFloat("Damage", this.damage);
    compound.putFloat("Damage1", this.damage1);
    compound.putFloat("Damage2", this.damage2);
  }

  @Override
  @Nullable
  public LivingEntity getOwner() {
    if (this.owner != null ||
      this.ownerUUID == null ||
      !(level() instanceof ServerLevel serverLevel) ||
      !(serverLevel.getEntity(this.ownerUUID) instanceof LivingEntity entity)) {
      return owner;
    }

    this.owner = entity;
    return owner;
  }

  public void setOwner(@Nullable LivingEntity owner) {
    this.owner = owner;
    this.ownerUUID = owner == null ? null : owner.getUUID();
  }

  @Override
  public void handleEntityEvent(byte id) {
    super.handleEntityEvent(id);
    if (id != 4) {
      return;
    }

    this.clientSideAttackStarted = true;
    if (isSilent()) {
      return;
    }
    float pitch = random.nextFloat() * 0.2F + 0.85F;
    level().playLocalSound(getX(), getY(), getZ(), SoundEvents.EVOKER_FANGS_ATTACK,
      getSoundSource(), 1.0F, pitch, false);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return anims;
  }

  public int getTargetNumber() {
    return targetNumber;
  }

  public float getDamage() {
    return damageCalculation(this.damage, this.damage1, this.damage2);
  }

  /**
   * 伤害计算
   */
  private float damageCalculation(float damage, float damage1, float damage2) {
    int number = this.targetNumber;
    if (number == 1) {
      return damage;
    }
    if (number >= 2) {
      damage = damage1;
    }
    if (number >= 6) {
      damage = damage2;
    }
    return damage;
  }

  public float getAnimationProgress(float partialTicks) {
    if (!clientSideAttackStarted) {
      return 0.0F;
    }
    int i = lifeTicks - 2;
    return i <= 0 ? 1.0F : 1.0F - ((float) i - partialTicks) / 20.0F;
  }

  /**
   * 是否可攻击
   */
  private boolean targetJudgment(Entity entity) {
    if (entity instanceof ItemEntity ||
      entity instanceof ExperienceOrb ||
      entity instanceof ParadiseLostSpikeweed ||
      entity instanceof Projectile) {
      return false;
    }
    LivingEntity owner = getOwner();
    return owner != null && !entity.getUUID().equals(owner.getUUID());
  }

  public static class TrainingRabbitsRenderer extends GeoEntityRenderer<ParadiseLostSpikeweed> {
    public TrainingRabbitsRenderer(EntityRendererProvider.Context context) {
      super(context, new ModGeoEntityModel<>("paradise_lost_spikeweed"));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ParadiseLostSpikeweed animatable) {
      return ModGeoEntityModel.texturePath("paradise_lost_spikeweed");
    }
  }
}
