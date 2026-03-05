package ctn.imaginarycraft.api.virtue;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.world.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.attachment.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * 谨慎
 */
public final class VirtuePrudence extends AbstractVirtue {
  private static final ResourceLocation AMPLITUDE_ID = ImaginaryCraft.modRl("justice_amplitude");

  public VirtuePrudence(final Player holder) {
    super(holder);
  }

  @Override
  public VirtueType getVirtue() {
    return VirtueType.PRUDENCE;
  }

  @Override
  public Map<AttributeInstance, Set<AttributeModifier>> getAffectedAttributeAndModifiers() {
    return Map.ofEntries(
      getAttributeAndModifiers(ModAttributes.MAX_RATIONALITY)
    );
  }

  @Override
  public Map<AttributeInstance, Float> getCorrelationAttributesHolder() {
    return Map.ofEntries(
      getAttributeAndValue(ModAttributes.MAX_RATIONALITY, 1f)
    );
  }

  @Override
  public AttributeInstance getPointsAttributeInstance() {
    return getAttribute(ModAttributes.PRUDENCE_POINTS);
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
    setAttributeModifier(ModAttributes.MAX_RATIONALITY, newPoints, AttributeModifier.Operation.ADD_VALUE);
  }

  public static class Serialize extends AbstractSerialize<VirtuePrudence> {

    @Override
    public VirtuePrudence createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider) {
      return new VirtuePrudence((Player) holder);
    }
  }

  public static class Sync extends AbstractSync<VirtuePrudence> {

    @Override
    public VirtuePrudence createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable final VirtuePrudence attachment) {
      return new VirtuePrudence((Player) holder);
    }
  }
}
