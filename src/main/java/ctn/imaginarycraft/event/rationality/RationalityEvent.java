package ctn.imaginarycraft.event.rationality;

import ctn.imaginarycraft.init.world.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public abstract class RationalityEvent extends PlayerEvent implements IModBusEvent {
  public RationalityEvent(Player player) {
    super(player);
  }

  public AttributeInstance getMax() {
    return getEntity().getAttribute(ModAttributes.MAX_RATIONALITY);
  }

  /**
   * 获取自然恢复效率
   */
  public AttributeInstance getNaturalRecoveryRate() {
    return getEntity().getAttribute(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME);
  }

  /**
   * 获取自然恢复量
   */
  public AttributeInstance getRecoveryAmount() {
    return getEntity().getAttribute(ModAttributes.RATIONALITY_RECOVERY_AMOUNT);
  }
}
