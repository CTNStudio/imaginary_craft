package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.sugar.*;
import ctn.imaginarycraft.api.world.item.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.client.gui.screen.config.*;

@Mixin(ItemsPreferenceScreen.class)
public abstract class ItemsPreferenceScreenMixin {
  @ModifyExpressionValue(method = "judgeItemPreference", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", ordinal = 0))
  private static boolean imaginarycraft$judgeItemPreference(boolean original, @Local(name = "item") Item item) {
    return original || item instanceof IMeleeEgoWeaponItem || item instanceof IRemoteEgoWeaponItem;
  }
}
