package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.common.item.ego.weapon.EgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class EgoWeaponItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<EgoWeaponItem> STANDARD_TRAINING_EGO = register(
    "standard_training_egostandard_training_ego_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> FOURTH_MATCH_FLAME = register(
    "fourth_match_flame_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> PENITENCE = register(
    "penitence_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> IN_THE_NAME_OF_LOVE_AND_HATE = register(
    "in_the_name_of_love_and_hate",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> BEAR_PAWS = register(
    "bear_paws_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SANGUINE_DESIRE = register(
    "sanguine_desire",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SOLITUDE = register(
    "solitude_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SYRINX = register(
    "syrinx_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SCREAMING_WEDGE = register(
    "screaming_wedge",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> MIMICRY = register(
    "mimicry_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HARMONY = register(
    "harmony_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> DA_CAPO = register(
    "da_capo_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> LOGGING = register(
    "logging_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> FROST_SPLINTER = register(
    "frost_splinter",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> LAMP = register(
    "lamp_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> GRINDER_MK4 = register(
    "grinder_mk4_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> GREEN_STEM = register(
    "green_stem_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> RED_EYES = register(
    "red_eyes_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HORN = register(
    "horn_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> PARADISE_LOST = register(
    "paradise_lost_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> CHRISTMAS = register(
    "christmas_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HORNET = register(
    "hornet_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> WRIST_CUTTER = register(
    "wrist_cutter_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SODA = register(
    "soda_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> FAINT_AROMA = register(
    "faint_aroma_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> REGRET = register(
    "regret_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> OUR_GALAXY = register(
    "our_galaxy",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> BEAK = register(
    "beak_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> CRIMSON_SCAR = register(
    "crimson_scar_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> COBALT_SCAR = register(
    "cobalt_scar_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> FRAGMENTS_FROM_SOMEWHERE = register(
    "fragments_from_somewhere_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> LIFE_FOR_A_DAREDEVIL = register(
    "life_for_a_daredevil_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> JUSTITIA = register(
    "justitia_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> TWILIGHT = register(
    "twilight_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> GOLD_RUSH = register(
    "gold_rush_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SPORE = register(
    "spore_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> LAETITIA = register(
    "laetitia_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SOLEMN_LAMENT = register(
    "solemn_lament_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> MAGIC_BULLET = register(
    "magic_bullet_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> BLACK_SWAN = register(
    "black_swan_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> ECSTASY = register(
    "ecstasy_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HEAVEN = register(
    "heaven_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> THE_SWORD_SHARPENED_WITH_TEARS = register(
    "the_sword_sharpened_with_tears_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> EXUVIAE = register(
    "exuviae_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SMILE = register(
    "smile_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> GAZE = register(
    "gaze_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> WINGBEAT = register(
    "wingbeat_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> LANTERN = register(
    "lantern_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HARVEST = register(
    "harvest_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> DIFFRACTION = register(
    "diffraction_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> CENSORED = register(
    "censored_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> TODAY_IS_EXPRESSION = register(
    "today_is_expression_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SOUND_OF_A_STAR = register(
    "sound_of_a_star_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> PLEASURE = register(
    "pleasure_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> ENGULFING_DREAM = register(
    "engulfing_dream_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> CHERRY_BLOSSOMS = register(
    "cherry_blossoms_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> FEATHER_OF_HONOR = register(
    "feather_of_honor_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> DISCORD = register(
    "discord_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> MOONLIGHT = register(
    "moonlight_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> PINK = register(
    "pink_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> SO_CUTE = register(
    "so_cute_weapon",
    "！！！",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> HYPOCRISY = register(
    "hypocrisy_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> ADORATION = register(
    "adoration_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> AMITA = register(
    "amita_weapon",
    "",
    new EgoWeaponItem.Builder());
  public static final DeferredItem<EgoWeaponItem> TOUGH = register(
    "tough_weapon",
    "",
    new EgoWeaponItem.Builder());

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static DeferredItem<EgoWeaponItem> register(String id, String zhName, EgoWeaponItem.Builder builder) {
    return register(id, zhName, EgoWeaponItem::new, builder);
  }

  @NotNull
  private static <I extends EgoWeaponItem> DeferredItem<I> register(String id, String zhName, Function<EgoWeaponItem.Builder, ? extends I> item, EgoWeaponItem.Builder builder) {
    DeferredItem<I> deferredItem = REGISTRY.register(id, () -> item.apply(builder));
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }
}
