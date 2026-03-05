package ctn.imaginarycraft.init.world.item;

import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.common.world.item.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.swords.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public final class ToolItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<Item> CREATIVE_RATIONALITY_TOOL = register(
    "creative_rationality_tool", "理智控制器", CreativeRationalityToolItem::new);
  public static final DeferredItem<Item> CHAOS_SWORD = register(
    "chaos_sword", "混沌刃", properties -> new ChaosKnifeItem(properties, new IMeleeEgoWeaponItem.Builder()
      .damage(7)
      .attackSpeed(-1.4F)));

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  @NotNull
  private static DeferredItem<Item> register(String name, String zhName, Item.Properties properties) {
    return register(name, zhName, Item::new, properties);
  }

  @NotNull
  private static DeferredItem<Item> register(String name, String zhName, Function<Item.Properties, ? extends Item> item) {
    return register(name, zhName, item, new Item.Properties());
  }

  @NotNull
  private static <I extends Item> DeferredItem<I> register(String name, String zhName,
                                                           Function<Item.Properties, ? extends I> item,
                                                           Item.Properties properties) {
    DeferredItem<I> deferredItem = REGISTRY.registerItem(name, item, properties);
    ZhCn.addI18nItemText(zhName, deferredItem);
    return deferredItem;
  }
}
