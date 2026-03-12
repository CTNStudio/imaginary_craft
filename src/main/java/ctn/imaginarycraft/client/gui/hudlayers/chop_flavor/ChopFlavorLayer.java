package ctn.imaginarycraft.client.gui.hudlayers.chop_flavor;


import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.client.gui.hudlayers.*;
import ctn.imaginarycraft.init.world.item.ego.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import yesman.epicfight.client.world.capabilites.entitypatch.player.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.*;

import java.util.*;
import java.util.function.*;

public class ChopFlavorLayer extends BasicHudLayer {
  public static final ChopFlavorLayer INSTANCE = new ChopFlavorLayer();
  private static final List<Pair<Predicate<ItemStack>, IChopFlavorBar>> list = new ArrayList<>();

  private Function<ItemStack, IChopFlavorBar> chopFlavorBarProvider;
  @Nullable
  private IChopFlavorBar activateBar;
  private ItemStack mainHandItemStack = net.minecraft.world.item.ItemStack.EMPTY;
  private LocalPlayerPatch localPlayerPatch;

  public static void init() {
    list.clear();
    list.add(Pair.of((itemStack) -> itemStack.is(EgoWeaponItems.RED_EYES_TACHI), new RedEyesTachiChopFlavor()));
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
    ItemStack mainHandItem = getPlayer().getMainHandItem();
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
