package ctn.singularity.lib.init.util;

import ctn.singularity.lib.init.LibAttachments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AttachmentRegisterUtil {

  protected static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name,
                                                                                              AttachmentType.@NotNull Builder<T> builder) {
    return register(name, builder::build);
  }

  protected static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name,
                                                                                              Function<Player, T> defaultValue,
                                                                                              @NotNull Function<AttachmentType.Builder<T>, AttachmentType.Builder<T>> builder) {
    return register(name, builder.apply(AttachmentType.builder(holder ->
      instanceofPlayer(defaultValue, holder, name)))::build);
  }

  @Contract("_, null, _ -> fail")
  protected static <T> T instanceofPlayer(@NotNull Function<Player, T> defaultValue, final IAttachmentHolder holder, String name) {
    assert holder instanceof Player : name + " can only be attached to a player";
    return defaultValue.apply((Player) holder);
  }

  private static <T> @NotNull DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name,
                                                                                            Supplier<AttachmentType<T>> builder) {
    return LibAttachments.REGISTRY.register(name, builder);
  }
}
