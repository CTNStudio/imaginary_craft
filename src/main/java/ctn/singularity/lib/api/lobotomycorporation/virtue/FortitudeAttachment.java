package ctn.singularity.lib.api.lobotomycorporation.virtue;

import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibAttributes;
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
 * 勇气
 */
public final class FortitudeAttachment extends AbstractVirtueAttachment {
  public static final ResourceLocation AMPLITUDE_ID = LibMain.modRl("fortitude_amplitude");

  public FortitudeAttachment(final Player holder) {
    super(holder);
  }

  @Override
  public Virtue getVirtue() {
    return Virtue.FORTITUDE;
  }

  @Override
  public Map<AttributeInstance, Set<AttributeModifier>> getAffectedAttributeAndModifiers() {
    return Map.ofEntries(
      getAttributeAndModifiers(Attributes.MAX_HEALTH)
    );
  }

  @Override
  public Map<AttributeInstance, Float> getCorrelationAttributesHolder() {
    return Map.ofEntries(
      getAttributeAndValue(Attributes.MAX_HEALTH, 1f)
    );
  }

  @Override
  public AttributeInstance getPointsAttributeInstance() {
    return getPlayer().getAttribute(LibAttributes.FORTITUDE_POINTS);
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
    setAttributeModifier(Attributes.MAX_HEALTH, newPoints, AttributeModifier.Operation.ADD_VALUE);
  }

  public static class Serialize extends AbstractSerialize<FortitudeAttachment> {

    @Override
    public FortitudeAttachment createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider) {
      return new FortitudeAttachment((Player) holder);
    }
  }

  public static class Sync extends AbstractSync<FortitudeAttachment> {

    @Override
    public FortitudeAttachment createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable final FortitudeAttachment attachment) {
      return new FortitudeAttachment((Player) holder);
    }
  }
}
