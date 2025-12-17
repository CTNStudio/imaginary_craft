package ctn.imaginarycraft.init.item;

import ctn.imaginarycraft.common.item.CreativeRationalityToolItem;
import ctn.imaginarycraft.common.item.ego.weapon.melee.ChaosKnifeItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class ToolItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<Item> CREATIVE_RATIONALITY_TOOL = register(
    "creative_rationality_tool", "理智控制器", CreativeRationalityToolItem::new);
  public static final DeferredItem<Item> CHAOS_SWORD = register(
    "chaos_sword", "混沌刃", properties -> new ChaosKnifeItem(properties, new MeleeEgoWeaponItem.Builder()
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
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }
}
