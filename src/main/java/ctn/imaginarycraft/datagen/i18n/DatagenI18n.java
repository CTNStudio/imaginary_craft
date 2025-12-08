package ctn.imaginarycraft.datagen.i18n;

import ctn.imaginarycraft.config.ConfigUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

  protected void addItemList(Map<DeferredItem<? extends Item>, String> map) {
    map.forEach((holder, zhName) -> add(holder.value(), zhName));
  }

  protected void addEntityList(Map<Holder<EntityType<?>>, String> map) {
    map.forEach((holder, zhName) -> add(holder.value(), zhName));
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
   * 玩家死亡消息翻译
   */
  protected void addPlayerDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath() + ".player", name);
  }
}
