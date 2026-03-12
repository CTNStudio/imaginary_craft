package ctn.imaginarycraft.common.world.item;

import net.minecraft.world.entity.*;
import net.neoforged.neoforge.common.*;

import java.util.function.*;

public class ModEggItem extends DeferredSpawnEggItem {

  public ModEggItem(Supplier<? extends EntityType<? extends Mob>> entityType, Properties properties) {
    super(entityType, 0, 0, properties);
  }
}
