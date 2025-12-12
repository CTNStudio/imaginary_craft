package ctn.imaginarycraft.common.item.weapon;

import com.google.common.base.Suppliers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class AbstractGunItem extends RangedWeaponItem {
  @CheckForNull
  protected final Supplier<BulletItem> bullet;

  public AbstractGunItem(Properties properties,
                         Builder builder,
                         @Nullable Supplier<BulletItem> bullet) {
    super(properties, builder, Objects.nonNull(bullet));
    this.bullet = Objects.nonNull(bullet) ? Suppliers.memoize(bullet::get) : null;
  }

  /**
   * 当生物持有子弹或无需判定是否持有子弹时，返回 true。
   */
  abstract public boolean canFire(LivingEntity entity, ItemStack stack);

  /**
   * 生物开火逻辑。
   */
  abstract public void fire(Level level, LivingEntity livingEntity, ItemStack stack);

  @Override
  public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack,
                        int remainingUseDuration) {
    super.onUseTick(level, livingEntity, stack, remainingUseDuration);

    if (canFire(livingEntity, stack)) {
      this.fire(level, livingEntity, stack);
    } else {
      livingEntity.stopUsingItem();
    }
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.BOW;
  }
}
