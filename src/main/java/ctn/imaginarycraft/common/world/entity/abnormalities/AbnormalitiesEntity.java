package ctn.imaginarycraft.common.world.entity.abnormalities;

import ctn.imaginarycraft.capability.entity.IAbnormalities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class AbnormalitiesEntity extends Mob implements IAbnormalities, GeoEntity {
  protected AbnormalitiesEntity(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.@NotNull Builder createAbnormalitiesAttributes() {
    return createMobAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1);
  }
}
