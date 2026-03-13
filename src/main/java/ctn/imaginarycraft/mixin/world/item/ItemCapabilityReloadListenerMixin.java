package ctn.imaginarycraft.mixin.world.item;

import com.google.gson.Gson;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.api.data.reloader.ItemCapabilityReloadListener;

@Mixin(ItemCapabilityReloadListener.class)
public abstract class ItemCapabilityReloadListenerMixin extends SimpleJsonResourceReloadListener {
  public ItemCapabilityReloadListenerMixin(Gson gson, String directory) {
    super(gson, directory);
  }
}
