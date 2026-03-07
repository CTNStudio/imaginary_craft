package ctn.imaginarycraft.common.world.entity.condition;

import com.ibm.icu.text.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import yesman.epicfight.api.utils.*;
import yesman.epicfight.api.utils.side.*;
import yesman.epicfight.client.gui.datapack.widgets.*;
import yesman.epicfight.data.conditions.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

import java.util.*;

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
