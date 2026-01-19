package ctn.imaginarycraft.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements GeoEntity {
  private final AnimatableInstanceCache imaginarycraft$cache = GeckoLibUtil.createInstanceCache(this);

  protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"))
  private void imaginarycraft$resetAttackStrengthTickerHead(CallbackInfo ci) {

  }

  @Inject(method = "resetAttackStrengthTicker", at = @At("TAIL"))
  private void imaginarycraft$resetAttackStrengthTickerTail(CallbackInfo ci) {

  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, (state) -> {
      return PlayState.CONTINUE;
    }));
    controllers.add(new AnimationController<>(this, (state) -> {
      return PlayState.CONTINUE;
    }));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return imaginarycraft$cache;
  }
}
