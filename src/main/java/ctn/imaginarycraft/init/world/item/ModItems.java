package ctn.imaginarycraft.init.world.item;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import ctn.imaginarycraft.init.world.item.ego.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public final class ModItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
    ToolItems.init(bus);
    WeaponItems.init(bus);
    ArmorItems.init(bus);
    EgoItems.init(bus);
  }

  @NotNull
  private static DeferredItem<Item> register(String name, String zhName, Item.Properties properties) {
    return register(name, zhName, Item::new, properties);
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
