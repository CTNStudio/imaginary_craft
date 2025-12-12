package ctn.imaginarycraft.common.item.weapon;

import ctn.imaginarycraft.core.ImaginaryCraft;
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
  public boolean canFire(LivingEntity entity, ItemStack stack) {
    if (!this.isConsumingBullets(entity, stack) || Objects.isNull(this.bullet)) {
      return true;
    }

    return entity instanceof Player player &&
      player.getInventory().hasAnyMatching(it -> it.is(this.bullet.get()));
  }

  @Override
  public void fire(Level level, LivingEntity livingEntity, ItemStack stack) {
    if (Objects.isNull(this.bullet)) {
      ImaginaryCraft.LOGGER.error("子弹不存在的情况下使用了必要子弹的枪械！");
      return;
    }

    final Projectile projectile = this.bullet.get()
      .asProjectile(level, livingEntity.position(), stack, livingEntity.getDirection());

    level.addFreshEntity(projectile);
  }
}
