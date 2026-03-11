package ctn.imaginarycraft.client.gui.hudlayers;


import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.client.gui.widget.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.init.world.item.ego.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import yesman.epicfight.client.world.capabilites.entitypatch.player.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.*;

import java.util.*;
import java.util.function.*;

public class ChopFlavorLayer extends BasicHudLayer {
  public static final ResourceLocation BOTTOM = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/bottom");
  public static final ResourceLocation SCABBARD = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard");
  public static final ResourceLocation SCABBARD_BROKEN0 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken0");
  public static final ResourceLocation SCABBARD_BROKEN1 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken1");
  public static final ResourceLocation SCABBARD_BROKEN2 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken2");

  public static final ChopFlavorLayer INSTANCE = new ChopFlavorLayer();
  private static final List<Pair<Predicate<ItemStack>, IChopFlavorBar>> list = new ArrayList<>();

  private Function<ItemStack, IChopFlavorBar> chopFlavorBarProvider;
  @Nullable
  private IChopFlavorBar activateBar;
  private ItemStack mainHandItemStack = net.minecraft.world.item.ItemStack.EMPTY;
  private LocalPlayerPatch localPlayerPatch;

  public static void init() {
    list.clear();
    list.add(Pair.of((itemStack) -> itemStack.is(EgoWeaponItems.RED_EYES_TACHI),
      (guiGraphics, deltaTracker, x, y) -> {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(1, 1, 1);
        pose.translate(-35f, 0, 0);

        LocalPlayer player = INSTANCE.player;
        MobEffectInstance effect = player.getEffect(ModMobEffects.RED_EYES_HUNTING);
        guiGraphics.blitSprite(BOTTOM, 0, 0, 70, 16);

        int value;
        int maxValue;
        if (effect == null) {
          value = 1;
          maxValue = 1;
        } else {
          // TODO EGO共鸣后改成 200
          maxValue = 100;
          value = maxValue - effect.getDuration();
        }

        ImageProgressBar.renderProgressBar(guiGraphics,
          SCABBARD,
          20, 0,
          20, 0,
          70, 16,
          value, maxValue,
          false, true);
        pose.popPose();
      }));
    INSTANCE.chopFlavorBarProvider = ConditionalProviderFactory.getProvider(null, list);
  }

  // 获取渲染进度
  public static float getRenderProgress(SkillContainer container, float partialTick) {
    boolean creative = container.getExecutor().getOriginal().isCreative();
    boolean fullstack = creative || container.isFull();
    return fullstack || container.isActivated() ? 1.0F : container.getResource(partialTick);
  }

  @Override
  public void init(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    super.init(guiGraphics, deltaTracker);
    ItemStack mainHandItem = player.getMainHandItem();
    if (mainHandItem != mainHandItemStack) {
      activateBar = chopFlavorBarProvider.apply(mainHandItem);
      mainHandItemStack = mainHandItem;
    }
  }

  @Override
  protected void sizeChange(int newScreenWidth, int newScreenHeight) {
    super.sizeChange(newScreenWidth, newScreenHeight);
    setX(newScreenWidth / 2);
    setY(newScreenHeight - newScreenHeight / 4);
  }

  @Override
  public void playerChange(LocalPlayer newPlayer) {
    super.playerChange(newPlayer);
    localPlayerPatch = EpicFightCapabilities.getEntityPatch(newPlayer, LocalPlayerPatch.class);
  }

  @Override
  protected void renderDrawLayer(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    if (activateBar != null) {
      activateBar.render(guiGraphics, deltaTracker, leftPos, topPos);
    }
  }

  public static abstract class ChopFlavorBar implements IChopFlavorBar {
    @Override
    public abstract void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y);
  }

  public interface IChopFlavorBar {
    void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y);
  }
}
