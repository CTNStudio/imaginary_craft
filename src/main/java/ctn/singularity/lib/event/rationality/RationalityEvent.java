package ctn.singularity.lib.event.rationality;

import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public abstract class RationalityEvent extends PlayerEvent implements IModBusEvent {
	public RationalityEvent(Player player) {
		super(player);
	}

	public AttributeInstance getMax() {
    return getEntity().getAttribute(LibAttributes.MAX_RATIONALITY);
	}

  /**
   * 获取自然恢复效率
   */
	public AttributeInstance getNaturalRecoveryRate() {
		return getEntity().getAttribute(LibAttributes.RATIONALITY_NATURAL_RECOVERY_RATE);
	}

  /**
   * 获取自然恢复量
   */
	public AttributeInstance getRecoveryAmount() {
		return getEntity().getAttribute(LibAttributes.RATIONALITY_RECOVERY_AMOUNT);
	}
}
