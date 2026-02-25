package ctn.imaginarycraft.init;

import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public final class ModWeaponCapabilityPresets {
  /**
   * 锤（大锤）
   */
  public static final Function<Item, WeaponCapability.Builder> HAMMER = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };

  /**
   * 梲（狼牙棒）
   */
  public static final Function<Item, WeaponCapability.Builder> MACE = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };

  /**
   * 加农炮
   */
  public static final Function<Item, WeaponCapability.Builder> CANNON = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };

  /**
   * 枪
   */
  public static final Function<Item, WeaponCapability.Builder> GUN = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };

  /**
   * 手枪
   */
  public static final Function<Item, WeaponCapability.Builder> PISTOL = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };

  /**
   * 来复枪
   */
  public static final Function<Item, WeaponCapability.Builder> RIFLE = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder();
    return builder;
  };
}
