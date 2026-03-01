package ctn.imaginarycraft.api.event.rationality;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;

// TODO 侵蚀伤害不造成理智显示
public abstract class RationalityModifyEvent extends RationalityEvent {
  private final float oldValue;

  public RationalityModifyEvent(final Player player, float oldValue) {
    super(player);
    this.oldValue = oldValue;
  }

  public float getOldValue() {
    return oldValue;
  }

  public abstract float getNewValue();

  /**
   * 可取消
   */
  public static class Pre extends RationalityModifyEvent implements ICancellableEvent {
    private float newValue;

    public Pre(final Player player, float oldValue, float newValue) {
      super(player, oldValue);
      this.newValue = newValue;
    }

    @Override
    public float getNewValue() {
      return newValue;
    }

    public void setNewValue(final float newValue) {
      this.newValue = newValue;
    }
  }

  /**
   * 不可取消
   */
  public static class Post extends RationalityModifyEvent {
    private final float newValue;

    public Post(final Player player, float oldValue, float newValue) {
      super(player, oldValue);
      this.newValue = newValue;
    }

    @Override
    public float getNewValue() {
      return newValue;
    }
  }

}
