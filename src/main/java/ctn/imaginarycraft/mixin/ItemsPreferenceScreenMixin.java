package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.IRemoteEgoWeaponItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.client.gui.screen.config.ItemsPreferenceScreen;

@Mixin(ItemsPreferenceScreen.class)
public abstract class ItemsPreferenceScreenMixin {
  @ModifyExpressionValue(method = "judgeItemPreference", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", ordinal = 0))
  private static boolean imaginarycraft$judgeItemPreference(boolean original, @Local(name = "item") Item item) {
    return original || item instanceof IMeleeEgoWeaponItem || item instanceof IRemoteEgoWeaponItem;
  }
}
