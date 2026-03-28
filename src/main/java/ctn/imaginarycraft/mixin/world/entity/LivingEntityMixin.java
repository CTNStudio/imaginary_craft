package ctn.imaginarycraft.mixin.world.entity;

import ctn.imaginarycraft.mixed.ILivingEntity;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.registry.entries.EpicFightAttributes;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension, ILivingEntity {

  public LivingEntityMixin(EntityType<?> entityType, Level level) {
    super(entityType, level);
  }

  /**
   * TODO 问题修复后异常这段代码
   * <p>
   * 修复史诗战斗神秘问题 <a href="https://github.com/Epic-Fight/epicfight/issues/2507">Abnormal Overwriting of Epic Fight Attributes on LivingEntity #2507</a>
   */
  @Inject(order = 10000, at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V")
  private void epicfight$constructor(EntityType<?> entityType, Level level, CallbackInfo info) {
    LivingEntity self = (LivingEntity) ((Object) this);
    EpicFightCapabilities.getUnparameterizedEntityPatch(self, HurtableEntityPatch.class).ifPresent(entitypatch -> {
      AttributeSupplier.Builder builder = AttributeSupplier.builder();

      self.getAttributes().supplier.instances.forEach((k, v) -> {
        builder.add(k, v.getBaseValue());
      });

      new AttributeMap(DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entityType)).supplier.instances.forEach((k, v) -> {
        builder.add(k, v.getBaseValue());
      });

      if (!builder.hasAttribute(Attributes.ATTACK_DAMAGE)) {
        builder.add(Attributes.ATTACK_DAMAGE);
      }

      if (!builder.hasAttribute(EpicFightAttributes.WEIGHT)) {
        builder.add(EpicFightAttributes.WEIGHT);
      }

      if (!builder.hasAttribute(EpicFightAttributes.IMPACT)) {
        builder.add(EpicFightAttributes.IMPACT);
      }

      if (!builder.hasAttribute(EpicFightAttributes.ARMOR_NEGATION)) {
        builder.add(EpicFightAttributes.ARMOR_NEGATION);
      }

      if (!builder.hasAttribute(EpicFightAttributes.MAX_STRIKES)) {
        builder.add(EpicFightAttributes.MAX_STRIKES);
      }

      if (!builder.hasAttribute(EpicFightAttributes.STUN_ARMOR)) {
        builder.add(EpicFightAttributes.STUN_ARMOR);
      }

      if (!builder.hasAttribute(EpicFightAttributes.ASSASSINATION_RESISTANCE)) {
        builder.add(EpicFightAttributes.ASSASSINATION_RESISTANCE);
      }

      if (!builder.hasAttribute(EpicFightAttributes.OFFHAND_ARMOR_NEGATION)) {
        builder.add(EpicFightAttributes.OFFHAND_ARMOR_NEGATION);
      }

      if (!builder.hasAttribute(EpicFightAttributes.OFFHAND_IMPACT)) {
        builder.add(EpicFightAttributes.OFFHAND_IMPACT);
      }

      if (!builder.hasAttribute(EpicFightAttributes.OFFHAND_MAX_STRIKES)) {
        builder.add(EpicFightAttributes.OFFHAND_MAX_STRIKES);
      }

      if (!builder.hasAttribute(EpicFightAttributes.OFFHAND_ATTACK_SPEED)) {
        builder.add(EpicFightAttributes.OFFHAND_ATTACK_SPEED);
      }

      self.getAttributes().supplier = builder.build();
    });
  }
}
