package ctn.imaginarycraft.common.world.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

public class ModEggItem extends DeferredSpawnEggItem {

  public ModEggItem(Supplier<? extends EntityType<? extends Mob>> entityType, Properties properties) {
    super(entityType, 0, 0, properties);
  }
}
