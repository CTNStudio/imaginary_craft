package ctn.singularity.lib.api.lobotomycorporation.virtue;

import ctn.singularity.lib.core.LibMain;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 正义
 */
public final class JusticeAttachment extends AbstractVirtueAttachment {
  private static final ResourceLocation AMPLITUDE_ID = LibMain.modRl("justice_amplitude");
  private static final double JUSTICE_MOVEMENT_SPEED = 0.001;
  private static final double JUSTICE_ATTACK_SPEED = 0.01;
  private static final double JUSTICE_SWIM_SPEED = 0.01;
  private static final double JUSTICE_FLIGHT_SPEED = 0.00013;
  private static final float VANILLA_FLYING_SPEED = 0.05f;

  private double flightSpeedBonus;

  public JusticeAttachment(final Player holder) {
    super(holder);
  }

  @Override
  public Virtue getVirtue() {
    return Virtue.JUSTICE;
  }

  @Override
  public Map<AttributeInstance, Set<AttributeModifier>> getAffectedAttributeAndModifiers() {
    return Map.ofEntries(
      getAttributeAndModifiers(Attributes.MOVEMENT_SPEED),
      getAttributeAndModifiers(Attributes.ATTACK_SPEED),
      getAttributeAndModifiers(NeoForgeMod.SWIM_SPEED)
    );
  }

  @Override
  public Set<Holder<Attribute>> getCorrelationAttributesHolder() {
    return Set.of(
      Attributes.MOVEMENT_SPEED,
      Attributes.ATTACK_SPEED,
      NeoForgeMod.SWIM_SPEED
    );
  }

  @Override
  public ResourceLocation getAmplitudeId() {
    return AMPLITUDE_ID;
  }

  @Override
  public int getRatingPoints() {
    return 0; // TODO
  }

  @Override
  public void updateTrigger() {
  }

  @Override
  public void updatePoints(final int newPoints) {
    // TODO 需要提供随机处理
    setAttributeModifier(Attributes.MOVEMENT_SPEED, newPoints * JUSTICE_MOVEMENT_SPEED, AttributeModifier.Operation.ADD_VALUE);
    setAttributeModifier(Attributes.ATTACK_SPEED, newPoints * JUSTICE_ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE);
    setAttributeModifier(NeoForgeMod.SWIM_SPEED, newPoints * JUSTICE_SWIM_SPEED, AttributeModifier.Operation.ADD_VALUE);
    setFlightSpeedBonus(newPoints);
  }

  public double getFlightSpeedBonus() {
    return flightSpeedBonus;
  }

  public void setFlightSpeedBonus(double flightSpeedBonus) {
    if (getFlightSpeedBonus() == flightSpeedBonus) {
      return;
    }
    updateFlightSpeedBonus(flightSpeedBonus);
    this.flightSpeedBonus = flightSpeedBonus;
    updateTrigger();
    syncData();
  }

  private void updateFlightSpeedBonus(double newFlightSpeedBonus) {
    // TODO 很明显不对
//    var abilities = getPlayer().getAbilities();
//    abilities.setFlyingSpeed(abilities.getFlyingSpeed() + (float) (newFlightSpeedBonus * JUSTICE_FLIGHT_SPEED));
  }

  public static class Serialize extends AbstractSerialize<JusticeAttachment> {

    @Override
    public JusticeAttachment createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider) {
      return new JusticeAttachment((Player) holder);
    }

    @Override
    public @NotNull JusticeAttachment read(final @NotNull IAttachmentHolder holder, final @NotNull CompoundTag nbt, final HolderLookup.@NotNull Provider provider) {
      var attachment = super.read(holder, nbt, provider);
      attachment.flightSpeedBonus = nbt.getDouble("flightSpeedBonus");
      return attachment;
    }

    @Override
    public @NotNull CompoundTag write(final @NotNull JusticeAttachment attachment, final HolderLookup.@NotNull Provider provider) {
      var nbt = super.write(attachment, provider);
      nbt.putDouble("flightSpeedBonus", attachment.flightSpeedBonus);
      return nbt;
    }
  }

  public static class Sync extends AbstractSync<JusticeAttachment> {
    @Override
    public JusticeAttachment createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable final JusticeAttachment attachment) {
      return new JusticeAttachment((Player) holder);
    }

    @Override
    public void write(final @NotNull RegistryFriendlyByteBuf buf, final @NotNull JusticeAttachment attachment, final boolean initialSync) {
      super.write(buf, attachment, initialSync);
      buf.writeDouble(attachment.flightSpeedBonus);
    }

    @Override
    public @NotNull JusticeAttachment read(final @NotNull IAttachmentHolder holder, final @NotNull RegistryFriendlyByteBuf buf, @Nullable JusticeAttachment attachment) {
      var newAttachment = super.read(holder, buf, attachment);
      newAttachment.flightSpeedBonus = buf.readDouble();
      return newAttachment;
    }
  }
}
