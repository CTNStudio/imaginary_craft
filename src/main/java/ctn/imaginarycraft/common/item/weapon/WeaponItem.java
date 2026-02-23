package ctn.imaginarycraft.common.item.weapon;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * 武器基类
 */
public abstract class WeaponItem extends Item implements IItemInvincibleTick {
  public static final ResourceLocation ENTITY_RANGE = ImaginaryCraft.modRl("weapon_entity_range");
  public static final ResourceLocation BLOCK_RANGE = ImaginaryCraft.modRl("weapon_block_range");
  private final int invincibleTick;

  public WeaponItem(Builder builder) {
    this(builder.buildProperties(), builder);
  }

  public WeaponItem(Properties properties, Builder builder) {
    super(properties.attributes(builder.getItemAttributeModifiers()).stacksTo(1));
    this.invincibleTick = builder.invincibleTick;
  }

  /**
   * 获取武器攻击时造成的无敌帧
   */
  @Override
  public int getInvincibleTick(ItemStack stack) {
    return invincibleTick;
  }

  @Override
  public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level,
                                @NotNull BlockPos pos, Player player) {
    return !player.isCreative();
  }

  public static class Builder {
    /**
     * 伤害
     */
    protected float damage;
    /**
     * 攻击速度
     */
    protected float attackSpeed;
    /**
     * 近战攻击距离 & 可以摸到方块的距离
     */
    protected float attackDistance;
    /**
     * 耐久
     */
    protected int durability;
    protected int invincibleTick;
    protected Properties properties = new Properties();

    public Properties buildProperties() {
      Properties properties = this.properties;
      int durability = this.durability;
      properties.attributes(getItemAttributeModifiers());
      if (durability > 0) {
        properties.durability(durability);
      }
      return properties;
    }

    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID,
        this.damage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID,
        this.attackSpeed, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ENTITY_INTERACTION_RANGE,
        ENTITY_RANGE, this.attackDistance, AttributeModifier.Operation.ADD_VALUE,
        EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.BLOCK_INTERACTION_RANGE, BLOCK_RANGE,
        this.attackDistance, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return builder.build();
    }

    public Builder durability(int durability) {
      this.durability = durability;
      return this;
    }

    public Builder invincibleTick(int invincibleTick) {
      this.invincibleTick = invincibleTick;
      return this;
    }

    public Builder damage(float damage) {
      this.damage = damage;
      return this;
    }

    public Builder damage(int damage) {
      this.damage = damage;
      return this;
    }

    public Builder attackSpeed(float attackSpeed) {
      this.attackSpeed = attackSpeed;
      return this;
    }

    public Builder attackDistance(float attackDistance) {
      this.attackDistance = attackDistance;
      return this;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }
  }
}
