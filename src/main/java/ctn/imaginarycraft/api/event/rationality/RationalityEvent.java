package ctn.imaginarycraft.api.event.rationality;

import ctn.imaginarycraft.init.world.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.fml.event.*;
import net.neoforged.neoforge.event.entity.player.*;

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
