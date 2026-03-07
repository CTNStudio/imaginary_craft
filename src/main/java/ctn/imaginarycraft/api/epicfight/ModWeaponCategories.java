package ctn.imaginarycraft.api.epicfight;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import net.minecraft.network.chat.*;
import yesman.epicfight.world.capabilities.item.*;

public enum ModWeaponCategories implements WeaponCategory {
  HAMMER("weapon_category." + ImaginaryCraft.modRlText("hammer"), "锤"),
  MACE("weapon_category." + ImaginaryCraft.modRlText("mace"), "梲"),
  CANNON("weapon_category." + ImaginaryCraft.modRlText("cannon"), "加农炮"),
  GUN("weapon_category." + ImaginaryCraft.modRlText("gun"), "枪"),
  PISTOL("weapon_category." + ImaginaryCraft.modRlText("pistol"), "手枪"),
  RIFLE("weapon_category." + ImaginaryCraft.modRlText("rifle"), "来复枪");

  private final Component translationKey;
  private final int id;
  private final String zhCnName;
  private final String key;

  ModWeaponCategories(String translationKey, String zhCnName) {
    this.key = translationKey;
    this.translationKey = Component.translatable(translationKey);
    this.zhCnName = zhCnName;
    this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    ZhCn.addI18nText(this.key, this.zhCnName);
  }

  @Override
  public Component getTranslatable() {
    return this.translationKey;
  }

  @Override
  public int universalOrdinal() {
    return this.id;
  }

  public String getZhCnName() {
    return zhCnName;
  }

  public String getKey() {
    return key;
  }
}
