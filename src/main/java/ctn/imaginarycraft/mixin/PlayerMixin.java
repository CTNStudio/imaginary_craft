package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.IPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayer {
  protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Unique
  @Override
  public int getImaginarycraft$AttackStrengthTicker() {
    return attackStrengthTicker;
  }

  @Unique
  @Override
  public void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker) {
    this.attackStrengthTicker = attackStrengthTicker;
  }

  @Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"))
  private void imaginarycraft$resetAttackStrengthTickerHead(CallbackInfo ci) {

  }

  @Inject(method = "resetAttackStrengthTicker", at = @At("TAIL"))
  private void imaginarycraft$resetAttackStrengthTickerTail(CallbackInfo ci) {

  }
}
