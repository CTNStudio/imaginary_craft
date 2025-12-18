package ctn.imaginarycraft.common.item.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 简单实现枪械。子弹是必要的。
 */
public class SimpleGunEntity extends AbstractGunItem {
  public SimpleGunEntity(Properties properties, Builder builder,
                         @Nonnull Supplier<BulletItem> bullet) {
    super(properties, builder, bullet);
  }

  @Override
  public boolean canShoot(LivingEntity entity, ItemStack stack) {
    if (!this.isConsumingBullets(entity, stack) || Objects.isNull(this.bullet)
      || !(entity instanceof Player player)) {
      return true;
    }

    return player.getInventory().hasAnyMatching(it -> it.is(this.bullet.get()));
  }

  @Override
  public void shoot(Level level, LivingEntity shooter, ItemStack stack, Projectile bullet) {
    bullet.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + shooter.getYRot(),
      0.0F, shooter.getYRot(), shooter.getXRot());
    level.addFreshEntity(bullet);
  }

  @Override
  public Supplier<BulletItem> getDefaultBullet() {
    return null;
  }
}
