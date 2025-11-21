package ctn.singularity.lib.api.lobotomycorporation.virtue;

import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 谨慎
 */
public final class PrudenceAttachment extends AbstractVirtueAttachment {
  private static final ResourceLocation AMPLITUDE_ID = LibMain.modRl("justice_amplitude");

  public PrudenceAttachment(final Player holder) {
    super(holder);
  }

  @Override
  public Virtue getVirtue() {
    return Virtue.PRUDENCE;
  }

  @Override
  public Map<AttributeInstance, Set<AttributeModifier>> getAffectedAttributeAndModifiers() {
    return Map.ofEntries(
      getAttributeAndModifiers(LibAttributes.MAX_RATIONALITY)
    );
  }

  @Override
  public Map<AttributeInstance, Float> getCorrelationAttributesHolder() {
    return Map.ofEntries(
      getAttributeAndValue(LibAttributes.MAX_RATIONALITY, 1f)
    );
  }

  @Override
  public AttributeInstance getPointsAttributeInstance() {
    return getAttribute(LibAttributes.PRUDENCE_POINTS);
  }

  @Override
  public ResourceLocation getAmplitudeId() {
    return AMPLITUDE_ID;
  }

  @Override
  public void updateTrigger() {

  }

  @Override
  public void updatePoints(final int newPoints) {
    // TODO 需要提供随机处理
    setAttributeModifier(LibAttributes.MAX_RATIONALITY, newPoints, AttributeModifier.Operation.ADD_VALUE);
  }

  public static class Serialize extends AbstractSerialize<PrudenceAttachment> {

    @Override
    public PrudenceAttachment createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider) {
      return new PrudenceAttachment((Player) holder);
    }
  }

  public static class Sync extends AbstractSync<PrudenceAttachment> {

    @Override
    public PrudenceAttachment createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable final PrudenceAttachment attachment) {
      return new PrudenceAttachment((Player) holder);
    }
  }
}
