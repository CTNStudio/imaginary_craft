package ctn.imaginarycraft.client.gui.hudlayers.chop_flavor;


import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.data.ConditionalProviderFactory;
import ctn.imaginarycraft.client.gui.hudlayers.BasicHudLayer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.item.ego.EgoWeaponItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ChopFlavorLayer extends BasicHudLayer {
  public static final ChopFlavorLayer INSTANCE = new ChopFlavorLayer();
  private static final LinkedHashMap<ResourceLocation, Pair<Predicate<ItemStack>, IChopFlavorBar>> list = new LinkedHashMap<>();

  private Function<ItemStack, IChopFlavorBar> chopFlavorBarProvider;
  @Nullable
  private IChopFlavorBar activateBar;
  private ItemStack mainHandItemStack = ItemStack.EMPTY;
  private LocalPlayerPatch localPlayerPatch;

  public static void init() {
    list.clear();
    list.put(ImaginaryCraft.modRl("red_eyes_tachi"), Pair.of((itemStack) -> itemStack.is(EgoWeaponItems.RED_EYES_TACHI), new RedEyesTachiChopFlavor()));
    INSTANCE.chopFlavorBarProvider = ConditionalProviderFactory.getProvider(null, list.values().stream().toList());
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

  public final void castSkill() {
    if (activateBar != null) {
      activateBar.castSkill();
    }
  }

  public interface IChopFlavorBar {
    void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y);

    default void castSkill() {
    }
  }

  public static abstract class ChopFlavorBar implements IChopFlavorBar {
    @Override
    public abstract void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y);
  }
}
