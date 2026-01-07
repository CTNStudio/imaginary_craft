package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.util.SoundEventsRegisterUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSoundEvents extends SoundEventsRegisterUtil {
  public static final DeferredRegister<SoundEvent> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.SOUND_EVENT);

  public static final Holder<SoundEvent> ARMOR_EQUIP_ZAYIN = registerForHolder("armor_equip_zayin", "item.armor.equip_zayin");
  public static final Holder<SoundEvent> ARMOR_EQUIP_TETH = registerForHolder("armor_equip_teth", "item.armor.equip_teth");
  public static final Holder<SoundEvent> ARMOR_EQUIP_HE = registerForHolder("armor_equip_he", "item.armor.equip_he");
  public static final Holder<SoundEvent> ARMOR_EQUIP_WAW = registerForHolder("armor_equip_waw", "item.armor.equip_waw");
  public static final Holder<SoundEvent> ARMOR_EQUIP_ALEPH = registerForHolder("armor_equip_aleph", "item.armor.equip_aleph");
  public static final Holder<SoundEvent> SOLEMN_LAMENT_WEAPON_ATTACK_BLACK = registerForHolder("solemn_lament_weapon_attack_black", "item.solemn_lament_weapon.attack.black");
  public static final Holder<SoundEvent> SOLEMN_LAMENT_WEAPON_ATTACK_WHITE = registerForHolder("solemn_lament_weapon_attack_white", "item.solemn_lament_weapon.attack.white");
  public static final Holder<SoundEvent> SOLEMN_LAMENT_WEAPON_STONGATTACK_BLACK = registerForHolder("solemn_lament_weapon_stongattack_black", "item.solemn_lament_weapon.stongattack.black");
  public static final Holder<SoundEvent> SOLEMN_LAMENT_WEAPON_STONGATTACK_WHITE = registerForHolder("solemn_lament_weapon_stongattack_white", "item.solemn_lament_weapon.stongattack.white");
}
