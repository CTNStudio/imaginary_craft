package ctn.imaginarycraft.init.item;

import ctn.imaginarycraft.common.item.ego.EgoCurioItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18.zhcn.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

public final class ModEgoCurios {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  private static <T extends EgoCurioItem> DeferredItem<T> register(
    EntityType<?> entityType,
    String zhName,
    CuriosType type,
    Function<EgoCurioItem.Builder, ? extends T> item,
    EgoCurioItem.Builder builder
  ) {
    return register("%s_curios_%s".formatted(entityType.getDescriptionId(), type), zhName, type, item, builder);
  }

  private static <T extends EgoCurioItem> DeferredItem<T> register(
    String name,
    String zhName,
    CuriosType type,
    Function<EgoCurioItem.Builder, ? extends T> item,
    EgoCurioItem.Builder builder
  ) {
    DeferredItem<T> deferredItem = REGISTRY.register(name, () -> item.apply(builder));
    type.addList(deferredItem);
    ZhCn.ITEM.put(deferredItem, zhName);
    return deferredItem;
  }

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  public enum CuriosType {
    HEADWEAR("headwear", DatagenItemTag.EGO_CURIOS_HEADWEAR),
    CHEEK("cheek", DatagenItemTag.EGO_CURIOS_HEAD),
    HEAD("head,", DatagenItemTag.EGO_CURIOS_HEAD),
    HINDBRAIN("hindbrain", DatagenItemTag.EGO_CURIOS_HINDBRAIN),
    EYE_AREA("eye_area", DatagenItemTag.EGO_CURIOS_EYE_AREA),
    FACE("face,", DatagenItemTag.EGO_CURIOS_FACE),
    MASK("mask,", DatagenItemTag.EGO_CURIOS_CHEEK),
    MOUTH("mouth", DatagenItemTag.EGO_CURIOS_MASK),
    NECK("neck,", DatagenItemTag.EGO_CURIOS_MOUTH),
    CHEST("chest", DatagenItemTag.EGO_CURIOS_NECK),
    HAND("hand", DatagenItemTag.EGO_CURIOS_CHEST),
    GLOVE("glove", DatagenItemTag.EGO_CURIOS_HAND),
    RIGHT_BACK("glove", DatagenItemTag.EGO_CURIOS_HAND),
    LEFT_BACK("glove", DatagenItemTag.EGO_CURIOS_HAND),
    ;
    private final String name;
    private final List<DeferredItem<EgoCurioItem>> list;

    CuriosType(final String name, final List<DeferredItem<EgoCurioItem>> list) {
      this.name = name;
      this.list = list;
    }

    public String getName() {
      return name;
    }

    public <T extends EgoCurioItem> void addList(DeferredItem<T> item) {
      this.list.add((DeferredItem<EgoCurioItem>) item);
    }
  }
}
