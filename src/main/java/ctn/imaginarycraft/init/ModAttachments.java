package ctn.imaginarycraft.init;

import com.mojang.serialization.*;
import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.api.virtue.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public final class ModAttachments {
  public static final DeferredRegister<AttachmentType<?>> REGISTRY = ImaginaryCraft.modRegister(NeoForgeRegistries.ATTACHMENT_TYPES);

  public static final DeferredHolder<AttachmentType<?>, AttachmentType<DelayTaskHolder>> DELAY_TASK_HOLDER = register("delay_task_holder",
    AttachmentType.builder(DelayTaskHolder::new));

  /**
   * 枪械蓄力值（主手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GUN_CHARGE_UP_TICK_MAIN_HAND = registerPlayer("gun_charge_up_tick_main_hand",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT));
  /**
   * 枪械蓄力值（副手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GUN_CHARGE_UP_TICK_OFF_HAND = registerPlayer("gun_charge_up_tick_off_hand",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT));
  /**
   * 枪械是否可以攻击（主手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IS_GUN_ATTACK_MAIN_HAND = registerPlayer("is_gun_left_key_attack_main_hand",
    (player) -> true, builder -> builder
      .sync(ByteBufCodecs.BOOL));
  /**
   * 枪械是否可以攻击（副手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IS_GUN_ATTACK_OFF_HAND = registerPlayer("is_gun_left_key_attack_off_hand",
    (player) -> true, builder -> builder
      .sync(ByteBufCodecs.BOOL));

  // TODO 待实装 魔弹使用数量
  /**
   * 魔弹使用数量
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> MAGIC_BULLET_USAGE_QUANTITY = registerPlayer("magic_bullet_usage_quantity",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT)
      .serialize(Codec.INT)
      .copyOnDeath());

  /**
   * 理智值
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> RATIONALITY = registerPlayer("rationality",
    (player) -> 0f, builder -> builder
      .serialize(Codec.FLOAT)
      .sync(ByteBufCodecs.FLOAT));

  /**
   * 理智值暂停恢复tick
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RATIONALITY_PAUSE_RECOVERY_TICK = registerPlayer("rationality_pause_recovery_tick",
    (player) -> 0, builder -> builder
      .serialize(Codec.INT)
      .sync(ByteBufCodecs.INT));

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueFortitude>> FORTITUDE = registerPlayer("fortitude",
    VirtueFortitude::new, builder -> builder
      .serialize(new VirtueFortitude.Serialize())
      .sync(new VirtueFortitude.Sync())
      .copyOnDeath());
  /**
   * 谨慎
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtuePrudence>> PRUDENCE = registerPlayer("prudence",
    VirtuePrudence::new, builder -> builder
      .serialize(new VirtuePrudence.Serialize())
      .sync(new VirtuePrudence.Sync())
      .copyOnDeath());
  /**
   * 自律
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueTemperance>> TEMPERANCE = registerPlayer("temperance",
    VirtueTemperance::new, builder -> builder
      .serialize(new VirtueTemperance.Serialize())
      .sync(new VirtueTemperance.Sync())
      .copyOnDeath());
  /**
   * 正义
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueJustice>> JUSTICE = registerPlayer("justice",
    VirtueJustice::new, builder -> builder
      .serialize(new VirtueJustice.Serialize())
      .sync(new VirtueJustice.Sync())
      .copyOnDeath());


  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerPlayer(
    final String name,
    final Function<Player, T> defaultValue
  ) {
    return registerPlayer(name, defaultValue, builder -> builder);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerPlayer(
    final String name,
    final Function<Player, T> defaultValue,
    final @NotNull Function<AttachmentType.Builder<T>, AttachmentType.Builder<T>> builder
  ) {
    return register(name, builder.apply(AttachmentType.builder(holder ->
      instanceofPlayer(defaultValue, holder, name)))::build);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerEntity(
    final String name,
    final Function<Entity, T> defaultValue
  ) {
    return registerEntity(name, defaultValue, builder -> builder);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerEntity(
    final String name,
    final Function<Entity, T> defaultValue,
    final @NotNull Function<AttachmentType.Builder<T>, AttachmentType.Builder<T>> builder
  ) {
    return register(name, builder.apply(AttachmentType.builder(holder ->
      instanceofEntity(defaultValue, holder, name)))::build);
  }

  @Contract("_, null, _ -> fail")
  private static <T> T instanceofPlayer(final @NotNull Function<Player, T> defaultValue, final IAttachmentHolder holder, final String name) {
    assert holder instanceof Player : name + " can only be attached to a player";
    return defaultValue.apply((Player) holder);
  }

  @Contract("_, null, _ -> fail")
  private static <T> T instanceofEntity(final @NotNull Function<Entity, T> defaultValue, final IAttachmentHolder holder, final String name) {
    assert holder instanceof Entity : name + " can only be attached to an entity";
    return defaultValue.apply((Entity) holder);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(
    final String name,
    final AttachmentType.@NotNull Builder<T> builder
  ) {
    return register(name, builder::build);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(
    final String name,
    final Supplier<AttachmentType<T>> builder
  ) {
    return ModAttachments.REGISTRY.register(name, builder);
  }
}
