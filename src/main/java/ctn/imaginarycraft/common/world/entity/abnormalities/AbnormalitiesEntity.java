package ctn.imaginarycraft.common.world.entity.abnormalities;

import ctn.imaginarycraft.core.capability.entity.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;

public abstract class AbnormalitiesEntity extends Mob implements IEntityAbnormalities, GeoEntity {
  protected AbnormalitiesEntity(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.@NotNull Builder createAbnormalitiesAttributes() {
    return createMobAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1);
  }
}
