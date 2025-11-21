package ctn.singularity.lib.api.lobotomycorporation.virtue;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractVirtueAttachment implements IVirtueAttachment {
  private final Player player;
  private int points;

  protected AbstractVirtueAttachment(Player holder) {
    this.player = holder;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public int getPoints() {
    return points;
  }

  @Override
  public void setPoints(final int points) {
    if (getPoints() == points) {
      return;
    }
    updatePoints(points);
    this.points = points;
    updateTrigger();
    if (!getPlayer().level().isClientSide()) {
      syncData();
    }
  }

  @Override
  public void modifyPoints(final int points) {
    if (points == 0) {
      return;
    }
    setPoints(getPoints() + points);
  }

  @Override
  public VirtueRating getRating() {
    return VirtueRating.getRating(getRatingPoints());
  }

  @Override
  public void syncData() {
    if (getPlayer().level().isClientSide()) {
      return;
    }
    getPlayer().syncData(getVirtue().getAttachmentTypeHolder().value());
  }

  /**
   * 获取属性值
   * @param attributeHolder 属性
   * @return 属性值
   */
  protected double getAttributeValue(Holder<Attribute> attributeHolder) {
    return getPlayer().getAttributeValue(attributeHolder);
  }

  /**
   * 获取属性
   * @param attributeHolder 属性
   * @return 属性
   */
  protected AttributeInstance getAttribute(Holder<Attribute> attributeHolder) {
    return getPlayer().getAttribute(attributeHolder);
  }

  /**
   * 获取属性基础值
   * @param attributeHolder 属性
   * @return 属性基础值
   */
  protected double getAttributeBaseValue(Holder<Attribute> attributeHolder) {
    return getPlayer().getAttributeBaseValue(attributeHolder);
  }

  /**
   * 获取属性修改器
   * @param attributeHolder 属性
   * @param modifierId 属性修改器id
   * @return 属性修改器
   */
  protected AttributeModifier getAttributeModifier(Holder<Attribute> attributeHolder, ResourceLocation modifierId) {
    return getAttribute(attributeHolder).getModifier(modifierId);
  }

  /**
   * 获取属性和属性修改器
   * @param attributeHolder 属性
   * @param modifierIdArray 属性修改器id
   * @return 属性和属性修改器
   */
  protected Map.Entry<AttributeInstance, Set<AttributeModifier>> getAttributeAndModifiers(Holder<Attribute> attributeHolder, ResourceLocation... modifierIdArray) {
    var attributeInstance = getAttribute(attributeHolder);
    return Map.entry(attributeInstance, Arrays.stream(modifierIdArray)
      .map(attributeInstance::getModifier)
      .filter(Objects::nonNull)
      .collect(Collectors.toCollection(ObjectArraySet::ofUnchecked)));
  }

  protected Map.Entry<AttributeInstance, Set<AttributeModifier>> getAttributeAndModifiers(Holder<Attribute> attributeHolder) {
    return getAttributeAndModifiers(attributeHolder, getAmplitudeId());
  }

  /**
   * 在原来的基础上修改属性修改器
   */
  protected void addModifyAttributeModifier(ResourceLocation amplitudeId, Holder<Attribute> attribute, double value) {
    var modifier = getAttribute(attribute).getModifier(amplitudeId);
    setAttributeModifier(amplitudeId, attribute, modifier == null ? value : modifier.amount() + value, AttributeModifier.Operation.ADD_VALUE);
  }

  /**
   * 在原来的基础上修改属性修改器
   */
  protected void addModifyAttributeModifier(Holder<Attribute> attribute, double value) {
    addModifyAttributeModifier(getAmplitudeId(), attribute, value);
  }

  protected void setAttributeModifier(ResourceLocation amplitudeId, Holder<Attribute> attribute, double value,AttributeModifier.Operation operation) {
    getAttribute(attribute).addOrUpdateTransientModifier(new AttributeModifier(amplitudeId, value, operation));
  }

  protected void setAttributeModifier(Holder<Attribute> attribute, double value, AttributeModifier.Operation operation) {
    setAttributeModifier(getAmplitudeId(), attribute, value, operation);
  }

  public static abstract class AbstractSerialize<T extends AbstractVirtueAttachment> implements IAttachmentSerializer<CompoundTag, T> {

    public abstract T createAttachment(final IAttachmentHolder holder, final CompoundTag nbt, final HolderLookup.Provider provider);

    @Override
    public @NotNull T read(final @NotNull IAttachmentHolder holder, final @NotNull CompoundTag nbt, final HolderLookup.@NotNull Provider provider) {
      T attachment = createAttachment(holder, nbt, provider);
      ((AbstractVirtueAttachment) attachment).points = nbt.getInt("points");
      return attachment;
    }

    @Override
    @NotNull
    public CompoundTag write(final @NotNull T attachment, final HolderLookup.@NotNull Provider provider) {
      var nbt = new CompoundTag();
      nbt.putInt("points", ((AbstractVirtueAttachment) attachment).points);
      return nbt;
    }
  }

  public static abstract class AbstractSync<T extends AbstractVirtueAttachment> implements AttachmentSyncHandler<T> {

    public abstract T createAttachment(final IAttachmentHolder holder, final RegistryFriendlyByteBuf buf, @Nullable T attachment);

    @Override
    public void write(final @NotNull RegistryFriendlyByteBuf buf, final @NotNull T attachment, final boolean initialSync) {
      buf.writeInt(((AbstractVirtueAttachment) attachment).points);
    }

    @Override
    @NotNull
    public T read(final @NotNull IAttachmentHolder holder, final @NotNull RegistryFriendlyByteBuf buf, @Nullable T attachment) {
      T newAttachment = createAttachment(holder, buf, attachment);
      ((AbstractVirtueAttachment) newAttachment).points = buf.readInt();
      return newAttachment;
    }
  }
}
