package ctn.imaginarycraft.mixin.client;

import com.llamalad7.mixinextras.expression.*;
import com.llamalad7.mixinextras.injector.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Gui.class)
public abstract class GuiMixin {

  @Shadow
  @Final
  private Minecraft minecraft;

  @Definition(id = "minecraft", field = "Lnet/minecraft/client/gui/Gui;minecraft:Lnet/minecraft/client/Minecraft;")
  @Definition(id = "options", field = "Lnet/minecraft/client/Minecraft;options:Lnet/minecraft/client/Options;")
  @Definition(id = "attackIndicator", method = "Lnet/minecraft/client/Options;attackIndicator()Lnet/minecraft/client/OptionInstance;")
  @Definition(id = "get", method = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;")
  @Definition(id = "CROSSHAIR", field = "Lnet/minecraft/client/AttackIndicatorStatus;CROSSHAIR:Lnet/minecraft/client/AttackIndicatorStatus;")
  @Expression("this.minecraft.options.attackIndicator().get() == CROSSHAIR")
  @ModifyExpressionValue(method = "renderCrosshair", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean imaginarycraft$renderCrosshair(boolean original) {
    if (GunWeaponUtil.is(minecraft.player)) {
      return false;
    }
    return original;
  }

  @Definition(id = "minecraft", field = "Lnet/minecraft/client/gui/Gui;minecraft:Lnet/minecraft/client/Minecraft;")
  @Definition(id = "options", field = "Lnet/minecraft/client/Minecraft;options:Lnet/minecraft/client/Options;")
  @Definition(id = "attackIndicator", method = "Lnet/minecraft/client/Options;attackIndicator()Lnet/minecraft/client/OptionInstance;")
  @Definition(id = "get", method = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;")
  @Definition(id = "HOTBAR", field = "Lnet/minecraft/client/AttackIndicatorStatus;HOTBAR:Lnet/minecraft/client/AttackIndicatorStatus;")
  @Expression("this.minecraft.options.attackIndicator().get() == HOTBAR")
  @ModifyExpressionValue(method = "renderItemHotbar", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean imaginarycraft$renderItemHotbar(boolean original) {
    if (GunWeaponUtil.is(minecraft.player)) {
      return false;
    }
    return original;
  }
}
