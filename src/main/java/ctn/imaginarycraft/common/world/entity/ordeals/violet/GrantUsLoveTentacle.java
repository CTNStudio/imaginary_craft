package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.jointpart.JointPartLivingEntity;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.registry.entries.EpicFightAttributes;

import java.util.List;

import static net.minecraft.world.entity.Mob.createMobAttributes;

// TODO 目前可能重世界加载的时候无法获取父实体，需要排除和修复
public class GrantUsLoveTentacle extends JointPartLivingEntity<GrantUsLove> implements IOrdealsVioletEntity {

	public GrantUsLoveTentacle(EntityType<? extends GrantUsLoveTentacle> entityType, Level level) {
		super(entityType, level);
	}

	public GrantUsLoveTentacle(Level level, GrantUsLove parent, @NotNull String partName, Joint... joints) {
		super(OrdealsEntityTypes.GRANT_US_LOVE_TENTACLE.get(), level, parent, partName, joints);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
			.add(Attributes.KNOCKBACK_RESISTANCE, 1)
			.add(Attributes.MAX_HEALTH, 50)
			.add(Attributes.ATTACK_DAMAGE, 7)
			.add(Attributes.MOVEMENT_SPEED, 0)
			.add(Attributes.ATTACK_KNOCKBACK, 1)
			.add(Attributes.GRAVITY, 0.1)
			.add(ModAttributes.PHYSICS_VULNERABLE, 0.8)
			.add(ModAttributes.SPIRIT_VULNERABLE, 2.0)
			.add(ModAttributes.EROSION_VULNERABLE, 0.8)
			.add(ModAttributes.THE_SOUL_VULNERABLE, 1)
			.add(EpicFightAttributes.IMPACT, 8)
			.add(EpicFightAttributes.MAX_STRIKES, 8);
	}

	@Override
	public boolean isItRecoverable() {
		return true;
	}

	@Override
	public boolean isDestructible() {
		return true;
	}

	@Override
	public boolean isTransmittingDamage() {
		return true;
	}

	@Override
	public boolean destructible() {
		return true;
	}

	@Override
	public boolean recoverable() {
		return true;
	}

	@Override
	public @NotNull Iterable<ItemStack> getArmorSlots() {
		return List.of();
	}

	@Override
	public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {

	}

	@Override
	public @NotNull HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}
}
