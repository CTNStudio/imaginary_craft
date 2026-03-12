package ctn.imaginarycraft.datagen.i18n;

import ctn.imaginarycraft.config.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.data.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;


public abstract class DatagenI18n extends LanguageProvider {
  public DatagenI18n(PackOutput output, String locale) {
    super(output, ImaginaryCraft.ID, locale);
  }

  public static List<LanguageProvider> init(PackOutput output) {
    List<LanguageProvider> list = new ArrayList<>();
    list.add(new ZhCn(output));
    return list;
  }

  public static @NotNull String getFormattedKey(String... key) {
    StringBuilder builder = new StringBuilder(ImaginaryCraft.ID);
    builder.append(".commands");
    for (String s : key) {
      builder.append(".").append(s);
    }
    return builder.toString();
  }

  @Override
  protected abstract void addTranslations();

  protected void addItemList(Map<Supplier<? extends Item>, String> map) {
    map.forEach((holder, zhName) -> add(holder.get(), zhName));
  }

  protected void addEntityList(Map<Supplier<? extends EntityType<?>>, String> map) {
    map.forEach((holder, zhName) -> add(holder.get(), zhName));
  }

  protected void addMobEffectList(Map<Supplier<? extends MobEffect>, String> map) {
    map.forEach((holder, zhName) -> add(holder.get(), zhName));
  }

  protected void addAttributeList(Map<Supplier<? extends Attribute>, String> map) {
    map.forEach((holder, zhName) -> add(holder.get(), zhName));
  }

  protected void addSoundEventList(Map<Supplier<? extends SoundEvent>, String> map) {
    map.forEach((holder, zhName) -> add(holder.get(), zhName));
  }

  protected void addJadePlugin(ResourceLocation pluginId, String name) {
    add("config.jade.plugin_" + pluginId.toLanguageKey(), name);
  }

  protected void addCurios(String curiosIdName, String name, String modifiersName) {
    add("curios.identifier." + curiosIdName, name);
    add("curios.modifiers." + curiosIdName, modifiersName);
  }

  protected void add(ModConfigSpec.ConfigValue<?> configValue, String value) {
    add(ConfigUtil.getTranslation(configValue.getPath().toArray(String[]::new)), value);
  }

  protected void add(ModConfigSpec.ConfigValue<?> configValue, String value, String tooltipValue) {
    add(configValue, value);
    add(ConfigUtil.getTranslation(configValue.getPath().toArray(String[]::new)) + ".tooltip", value);
  }

  protected <T> void add(DataComponentType<T> dataComponentType, String name) {
    add(dataComponentType.toString(), name);
  }

  /**
   * 生物属性翻译
   */
  protected void add(Attribute attributeHolder, String name) {
    add(attributeHolder.getDescriptionId(), name);
  }

  /**
   * 死亡消息翻译
   */
  protected void addDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath(), name);
  }

  /**
   * 声音字幕翻译
   */
  protected void addSoundEvent(Holder<SoundEvent> damageType, String name) {
    add(damageType.value(), name);
  }

  public void add(SoundEvent damageType, String name) {
    add(DatagenSoundDefinitionsProvider.getSubtitle(damageType), name);
  }

  /**
   * 玩家死亡消息翻译
   */
  protected void addPlayerDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath() + ".player", name);
  }
}
