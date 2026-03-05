package ctn.imaginarycraft.init.world.item.ego;

import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.common.world.item.ego.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public final class EgoItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
    EgoCurioItems.init(bus);
    EgoWeaponItems.init(bus);
    EgoArmorItems.init(bus);
  }

  private static <T extends Item & IEgoItem> DeferredItem<T> register(
    String id,
    String zhName,
    Item.Properties properties
  ) {
    return (DeferredItem<T>) register(id, zhName, EgoItem::new, properties);
  }

  @NotNull
  private static <I extends Item & IEgoItem> DeferredItem<I> register(
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
