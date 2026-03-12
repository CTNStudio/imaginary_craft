package ctn.imaginarycraft.init.world.item;

import ctn.imaginarycraft.common.world.item.ModEggItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.init.world.entity.AbnormalitiesEntityTypes;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class AbnormalitiesSpawnEggs {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<Item> GRANT_US_LOVE_SPAWN_EGG = register(
    "grant_us_love_spawn_egg",
    "\"请给我们爱\" 生物蛋",
    properties -> new ModEggItem(AbnormalitiesEntityTypes.GRANT_US_LOVE, properties),
    new Item.Properties()
  );

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static DeferredItem<Item> register(
    String id,
    String zhName,
    Item.Properties properties
  ) {
    return register(id, zhName, Item::new, properties);
  }

  @NotNull
  private static <I extends Item> DeferredItem<I> register(
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
