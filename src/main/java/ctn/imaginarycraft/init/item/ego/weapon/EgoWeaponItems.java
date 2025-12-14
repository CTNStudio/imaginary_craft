package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class EgoWeaponItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static void init(IEventBus bus) {
    EgoMeleeWeaponItems.init(bus);
    EgoRemoteWeaponItems.init(bus);
    EgoSpecialWeaponItems.init(bus);
    REGISTRY.register(bus);
  }

  static <I extends EgoWeaponItem> @NotNull DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                     Type type,
                                                                     Function<EgoWeaponItem.Builder, ? extends I> item,
                                                                     EgoWeaponItem.Builder builder) {
    DeferredItem<I> deferredItem = REGISTRY.register(id, () -> item.apply(builder));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    DatagenItemTag.EGO_WEAPON.add(deferredItem);
    type.addItem(deferredItem);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }

  static float minuteToSpeedConversion(float attackSpeedTick) {
    return 20.0f / (attackSpeedTick * 20) - 4;
  }
}
