package ctn.imaginarycraft.api.lobotomycorporation.virtue;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttachments;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 自律
 */
public final class VirtueTemperance extends AbstractVirtue {
  public static final ResourceLocation AMPLITUDE_ID = ImaginaryCraft.modRl("temperance_amplitude");
  private static final double TEMPERANCE_BLOCK_BREAK_SPEED = 0.02;
  private static final double TEMPERANCE_KNOCKBACK_SPEED = 0.015;

  public VirtueTemperance(final Player holder) {
    super(holder);
  }

  @Override
  public Virtue getVirtue() {
    return Virtue.TEMPERANCE;
  }

  @Override
  public Map<AttributeInstance, Set<AttributeModifier>> getAffectedAttributeAndModifiers() {
    return Map.ofEntries(
      getAttributeAndModifiers(Attributes.BLOCK_BREAK_SPEED),
      getAttributeAndModifiers(Attributes.ATTACK_KNOCKBACK)
    );
  }

  @Override
  public Map<AttributeInstance, Float> getCorrelationAttributesHolder() {
    return Map.ofEntries(
      getAttributeAndValue(Attributes.BLOCK_BREAK_SPEED, 1f),
      getAttributeAndValue(Attributes.ATTACK_KNOCKBACK, 1f)
    );
  }

  @Override
  public AttributeInstance getPointsAttributeInstance() {
    return getAttribute(ModAttributes.TEMPERANCE_POINTS);
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
  public void syncData() {
    getPlayer().syncData(ModAttachments.TEMPERANCE);
  }

  @Override
  public void updateTrigger() {
  }

  @Override
  public void updatePoints(final int newPoints) {
    // TODO 需要提供随机处理
    // 挖掘速度
    setAttributeModifier(Attributes.BLOCK_BREAK_SPEED, newPoints * TEMPERANCE_BLOCK_BREAK_SPEED, AttributeModifier.Operation.ADD_VALUE);
    // 击退
    setAttributeModifier(Attributes.ATTACK_KNOCKBACK, newPoints * TEMPERANCE_KNOCKBACK_SPEED, AttributeModifier.Operation.ADD_VALUE);
  }

  public static class Serialize extends AbstractSerialize<VirtueTemperance> {

    @Override
    public VirtueTemperance createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider) {
      return new VirtueTemperance((Player) holder);
    }
  }

  public static class Sync extends AbstractSync<VirtueTemperance> {

    @Override
    public VirtueTemperance createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable final VirtueTemperance attachment) {
      return new VirtueTemperance((Player) holder);
    }
  }
}
