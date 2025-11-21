package ctn.singularity.lib.common.entity.abnos;

import ctn.singularity.lib.capability.entity.IAbnos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class AbnosEntity extends Mob implements IAbnos, GeoEntity {
  protected AbnosEntity(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.@NotNull Builder createAbnosAttributes() {
    return createMobAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1);
  }
}
