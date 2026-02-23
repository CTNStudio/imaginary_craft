package ctn.imaginarycraft.init;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.util.DataComponentsRegisterUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModDataComponents extends DataComponentsRegisterUtil {
  public static final DeferredRegister<DataComponentType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.DATA_COMPONENT_TYPE);
  public static final Supplier<DataComponentType<LcDamageType>> LC_DAMAGE_TYPE = register("lobotomy_corporation_damage_type",
    LcDamageType.CODEC, LcDamageType.STREAM_CODEC, true);
  public static final Supplier<DataComponentType<Boolean>> MODE_BOOLEAN = recordBoolean("mode_boolean", true);
  /**
   * 是否正在受到抑制器的影响属性
   */
  public static final Supplier<DataComponentType<Boolean>> IS_RESTRAIN = recordBoolean("is_restrain", true);
  /**
   * 物品四德属性能力使用要求
   */
  public static final Supplier<DataComponentType<ItemVirtueUsageReq>> ITEM_VIRTUE_USAGE_REQ = register("item_virtue_usage_req",
    ItemVirtueUsageReq.CODEC, ItemVirtueUsageReq.STREAM_CODEC, true);
}
