package ctn.imaginarycraft.init.util;

import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AttachmentRegisterUtil {

  protected static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerPlayer(
    final String name,
    final Function<Player, T> defaultValue
  ) {
    return registerPlayer(name, defaultValue, builder -> builder);
  }

  protected static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerPlayer(
    final String name,
    final Function<Player, T> defaultValue,
    final @NotNull Function<AttachmentType.Builder<T>, AttachmentType.Builder<T>> builder
  ) {
    return register(name, builder.apply(AttachmentType.builder(holder ->
      instanceofPlayer(defaultValue, holder, name)))::build);
  }

  @Contract("_, null, _ -> fail")
  protected static <T> T instanceofPlayer(final @NotNull Function<Player, T> defaultValue, final IAttachmentHolder holder, final String name) {
    assert holder instanceof Player : name + " can only be attached to a player";
    return defaultValue.apply((Player) holder);
  }

  protected static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(
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
