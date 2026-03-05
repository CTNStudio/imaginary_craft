package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.*;
import ctn.imaginarycraft.config.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class GuiLayersEvents {
  @SubscribeEvent
  public static void renderGuiLayerEventPre(RenderGuiLayerEvent.Pre event) {
    ResourceLocation name = event.getName();
    Minecraft instance = Minecraft.getInstance();

    // 关闭原版的血条
    if (name.equals(VanillaGuiLayers.PLAYER_HEALTH)) {
      if (ModConfig.CLIENT.enableNewHealthBar.get()) {
        event.setCanceled(true);
      }
      return;
    }

    if (name.equals(ModGuiLayers.SCREEN_FILTER)) {
      if (!ModConfig.CLIENT.enableLowRationalityFilter.get()) {
        event.setCanceled(true);
      }
      return;
    }

    if (name.equals(ModGuiLayers.LC_DAMAGE_SCREEN_FILTER)) {
      if (!ModConfig.CLIENT.enableLcColorDamageFilter.get()) {
        event.setCanceled(true);
      }
      return;
    }

    if (name.equals(ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_CROSSHAIR) || name.equals(ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_HOTBAR)) {
      switch (instance.options.attackIndicator().get()) {
        case CROSSHAIR -> {
          if (name.equals(ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_HOTBAR)) {
            event.setCanceled(true);
          }
        }
        case HOTBAR -> {
          if (name.equals(ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_CROSSHAIR)) {
            event.setCanceled(true);
          }
        }
        default -> {
          event.setCanceled(true);
          return;
        }
      }
      if (instance.player != null && !GunWeaponUtil.is(instance.player)) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public static void renderGuiLayerEventPost(RenderGuiLayerEvent.Post event) {

  }
}
