package ctn.imaginarycraft.init.item;

import ctn.imaginarycraft.common.item.ego.EgoItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18.zhcn.ZhCn;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class ModEgos {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }


  private static DeferredItem<EgoItem> registerEgo(
    String name,
    String zhName,
    Item.Properties properties
  ) {
    return register(name, zhName, EgoItem::new, properties);
  }

  @NotNull
  private static <I extends EgoItem> DeferredItem<I> register(
    String name,
    String zhName,
    Function<Item.Properties, ? extends I> item,
    Item.Properties properties
  ) {
    DeferredItem<I> deferredItem = REGISTRY.registerItem(name, item, properties);
    ZhCn.ITEM.put(deferredItem, zhName);
    return deferredItem;
  }

}
