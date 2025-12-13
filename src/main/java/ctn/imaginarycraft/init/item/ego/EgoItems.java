package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.common.item.ego.EgoItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.init.item.ego.weapon.EgoMeleeWeaponItems;
import ctn.imaginarycraft.init.item.ego.weapon.EgoRemoteWeaponItems;
import ctn.imaginarycraft.init.item.ego.weapon.EgoSpecialWeaponItems;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class EgoItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
    EgoCurioItems.init(bus);
    EgoMeleeWeaponItems.init(bus);
    EgoRemoteWeaponItems.init(bus);
    EgoSpecialWeaponItems.init(bus);
    EgoArmorItems.init(bus);
  }

  private static DeferredItem<EgoItem> register(
    String id,
    String zhName,
    Item.Properties properties
  ) {
    return register(id, zhName, EgoItem::new, properties);
  }

  @NotNull
  private static <I extends EgoItem> DeferredItem<I> register(
    String id,
    String zhName,
    Function<Item.Properties, ? extends I> item,
    Item.Properties properties
  ) {
    DeferredItem<I> deferredItem = REGISTRY.registerItem(id, item, properties);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }
}
