package ctn.imaginarycraft.api.epicfight.condition;

import com.ibm.icu.text.MessageFormat;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import yesman.epicfight.api.utils.ParseUtil;
import yesman.epicfight.api.utils.side.ClientOnly;
import yesman.epicfight.client.gui.datapack.widgets.PopupBox;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MobEffectsCondition extends Condition.EntityPatchCondition {
  private Holder<MobEffect> effectHolder;

  @Override
  public Condition<LivingEntityPatch<?>> read(CompoundTag tag) throws IllegalArgumentException {
    String effectName = this.assertTag("effect", "string", tag, StringTag.class, CompoundTag::getString);

    Optional<Holder.Reference<MobEffect>> holder = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(effectName));
    if (holder.isEmpty()) {
      throw new NoSuchElementException(MessageFormat.format("{} condition error: Effect named {} does not exist", this.getClass().getSimpleName(), effectName));
    }
    effectHolder = holder.get();
    return this;
  }

  @Override
  public CompoundTag serializePredicate() {
    CompoundTag tag = new CompoundTag();
    tag.putString("effect", effectHolder.getRegisteredName());

    return tag;
  }

  @Override
  public boolean predicate(LivingEntityPatch<?> target) {
    return target.getOriginal().hasEffect(effectHolder);
  }

  @Override
  @ClientOnly
  public List<ParameterEditor> getAcceptingParameters(Screen screen) {
    var popupBox = new PopupBox.RegistryPopupBox<>(
      screen,
      screen.getMinecraft().font,
      0, 0, 0, 0,
      null,
      null,
      Component.literal("effect"),
      BuiltInRegistries.MOB_EFFECT,
      null);
    return List.of(ParameterEditor.of(skill ->
        StringTag.valueOf(skill.toString()), tag ->
        BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(ParseUtil.nullOrToString(tag, Tag::getAsString))),
      popupBox));
  }
}
