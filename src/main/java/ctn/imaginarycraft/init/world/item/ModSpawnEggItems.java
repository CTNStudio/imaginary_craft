package ctn.imaginarycraft.init.world.item;

import ctn.imaginarycraft.common.world.item.ModEggItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModSpawnEggItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<SpawnEggItem> GRANT_US_LOVE_SPAWN_EGG = register(
    "grant_us_love_spawn_egg", "“请给我们爱”刷怪蛋", OrdealsEntityTypes.GRANT_US_LOVE);
  public static final DeferredItem<SpawnEggItem> FRUIT_OF_UNDERSTANDING_EGG = register(
    "fruit_of_understanding_spawn_egg", "“理解的果实”刷怪蛋", OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING);

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static DeferredItem<SpawnEggItem> register(
    String id,
    String zhName,
    Supplier<? extends EntityType<? extends Mob>> entityType
  ) {
    return register(id, zhName, entityType, new Item.Properties());
  }

  private static DeferredItem<SpawnEggItem> register(
    String id,
    String zhName,
    Supplier<? extends EntityType<? extends Mob>> entityType,
    Item.Properties properties
  ) {
    return register(id, zhName, itemProperties -> new ModEggItem(entityType, properties), properties);
  }

  private static <I extends SpawnEggItem> DeferredItem<I> register(
    String id,
    String zhName,
    Function<Item.Properties, ? extends I> item,
    Item.Properties properties
  ) {
    DeferredItem<I> deferredItem = REGISTRY.registerItem(id, item, properties);
    ZhCn.addI18nItemText(zhName, deferredItem);
    return deferredItem;
  }
}
