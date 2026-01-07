package ctn.imaginarycraft.api;

import com.mojang.datafixers.util.Function3;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class PlayerTimingRun {
  private final Player player;
  private final Map<ResourceLocation, TimingRun> runList = new LinkedHashMap<>();

  public PlayerTimingRun(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public Map<ResourceLocation, TimingRun> getRunList() {
    return runList;
  }

  public void tick() {
    Iterator<TimingRun> iterator = runList.values().iterator();
    while (iterator.hasNext()) {
      TimingRun consumer = iterator.next();
      if (consumer.isRemoved) {
        iterator.remove();
      }
      consumer.run(this);
    }
  }

  public void addTimingRun(ResourceLocation id, TimingRun timingRun) {
    runList.put(id, timingRun);
  }

  public void addTimingRun(EquipmentSlot slot, TimingRun timingRun) {
    addTimingRun(ImaginaryCraft.modRl(slot.getName()), timingRun);
  }

  public void addTimingRun(InteractionHand handUsed, TimingRun timingRun) {
    addTimingRun(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, timingRun);
  }

  public void removeTimingRun(ResourceLocation id) {
    runList.remove(id);
  }

  public void removeTimingRun(EquipmentSlot slot) {
    removeTimingRun(ImaginaryCraft.modRl(slot.getName()));
  }

  public void removeTimingRun(InteractionHand handUsed) {
    removeTimingRun(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
  }

  public void removeAllTimingRun() {
    runList.clear();
  }

  public static PlayerTimingRun getInstance(Player player) {
    return player.getData(ModAttachments.PLAYER_TIMING_RUN);
  }

  public static TimingRun createTimingRun(Function<Player, Integer> resultRun, int tick) {
    return createTimingRunBilder().build(resultRun, tick);
  }

  public static TimingRun.Builder createTimingRunBilder() {
    return PlayerTimingRun.TimingRun.Builder.create();
  }

  public static class TimingRun {
    private final Function3<Integer, Integer, Player, Integer> tickRun;
    private final Function<Player, Integer> resultRun;
    private int remainingTick;
    private boolean isRemoved;
    private final int maxTick;

    private TimingRun(Function3<Integer, Integer, Player, Integer> tickRun, Function<Player, Integer> resultRun, int tick) {
      this.tickRun = tickRun;
      this.resultRun = resultRun;
      this.remainingTick = tick;
      this.maxTick = tick;
    }

    public void run(PlayerTimingRun playerTimingRun) {
      if (isRemoved) {
        return;
      }

      remainingTick = tickRun.apply(remainingTick, maxTick, playerTimingRun.getPlayer());
      if (remainingTick <= 0) {
        remainingTick = resultRun.apply(playerTimingRun.getPlayer());
      }

      if (remainingTick <= 0) {
        isRemoved = true;
      }
    }

    public static class Builder {
      public static final Function3<Integer, Integer, Player, Integer> DEFAULT = (tick, maxTick, playerTimingRun) -> tick - 1;
      private Function3<Integer, Integer, Player, Integer> tickRun = DEFAULT;

      private Builder() {
      }

      public static Builder create() {
        return new Builder();
      }

      public Builder tickRun(Function3<Integer, Integer, Player, Integer> tickRun) {
        this.tickRun = tickRun;
        return this;
      }

      public TimingRun build(Function<Player, Integer> resultRun, int maxTick) {
        return new TimingRun(tickRun, resultRun, maxTick);
      }
    }
  }
}
