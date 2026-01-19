package ctn.imaginarycraft.api;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class DelayTaskHolder {
  private final IAttachmentHolder attachmentHolder;
  private final Map<ResourceLocation, ITask> runList = new LinkedHashMap<>();

  public DelayTaskHolder(IAttachmentHolder attachmentHolder) {
    this.attachmentHolder = attachmentHolder;
  }

  public IAttachmentHolder getAttachmentHolder() {
    return attachmentHolder;
  }

  public Map<ResourceLocation, ITask> getRunList() {
    return runList;
  }

  public void tick() {
    if (runList.isEmpty()) {
      return;
    }
    Iterator<ITask> iterator = new HashSet<>(runList.values()).iterator();
    while (iterator.hasNext()) {
      ITask consumer = iterator.next();
      if (consumer.isRemoved()) {
        iterator.remove();
        continue;
      }
      consumer.run(this);
    }
  }

  public void addTask(ResourceLocation id, ITask task) {
    runList.put(id, task);
  }

  /**
   * 通过该方法添加的任务会在对应槽位的物品更替时移除
   */
  public void addTask(EquipmentSlot slot, ITask task) {
    addTask(ImaginaryCraft.modRl(slot.getName()), task);
  }

  /**
   * 通过该方法添加的任务会在对应手的物品更替时移除
   */
  public void addTask(InteractionHand handUsed, ITask task) {
    addTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, task);
  }

  /**
   * 通过该方法添加的任务会在对应槽位的物品更替时移除
   */
  public void addTask(EquipmentSlot slot, String name, ITask task) {
    addTask(ImaginaryCraft.modRl(slot.getName() + "." + name), task);
  }

  /**
   * 通过该方法添加的任务会在对应手的物品更替时移除
   */
  public void addTask(InteractionHand handUsed, String name, ITask task) {
    addTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, name, task);
  }

  public void removeTask(ResourceLocation id) {
    if (!containsTask(id)) {
      return;
    }
    runList.remove(id);
  }

  /**
   * 使用此方法会移除对应槽位的任务包括相关的
   */
  public void removeTask(EquipmentSlot slot) {
    if (containsTask(slot).isEmpty()) {
      return;
    }
    for (ResourceLocation key : new HashSet<>(runList.keySet())) {
      if (key.getPath().startsWith(slot.getName())) {
        runList.remove(key);
      }
    }
  }

  public void removeTask(EquipmentSlot slot, String name) {
    if (!containsTask(slot, name)) {
      return;
    }
    removeTask(ImaginaryCraft.modRl(slot.getName() + "." + name));
  }

  /**
   * 使用此方法会移除对应手的任务包括相关的
   */
  public void removeTask(InteractionHand handUsed) {
    removeTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
  }

  public void removeTask(InteractionHand handUsed, String name) {
    removeTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, name);
  }

  public boolean containsTask(ResourceLocation id) {
    return runList.containsKey(id);
  }

  /**
   * 如果返回的是空集合就表示该槽位没有任务
   */
  public Set<ResourceLocation> containsTask(EquipmentSlot slot) {
    return runList.keySet().stream().filter(key -> key.getPath().startsWith(slot.getName())).collect(Collectors.toSet());
  }

  public boolean containsTask(EquipmentSlot slot, String name) {
    return runList.containsKey(ImaginaryCraft.modRl(slot.getName() + "." + name));
  }

  /**
   * 如果返回的是空集合就表示该槽位没有任务
   */
  public Set<ResourceLocation> containsTask(InteractionHand handUsed) {
    return containsTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
  }

  public boolean containsTask(InteractionHand handUsed, String name) {
    return containsTask(handUsed == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, name);
  }

  public void removeAllTask() {
    runList.clear();
  }

  public static DelayTaskHolder of(AttachmentHolder attachmentHolder) {
    return attachmentHolder.getData(ModAttachments.DELAY_TASK_HOLDER);
  }

  public static ITask.Builder createTaskBilder() {
    return ITask.Builder.create();
  }

  public interface ITask {
    void run(DelayTaskHolder delayTaskHolder);

    boolean isRemoved();

    /**
     * 运行任务类，isRemoved为true时将在下一刻移除该任务
     */
    class BaseTask implements ITask {
      protected final ResultRun resultRun;
      protected int tick = 0;
      protected final int maxTick;
      protected int repeatCount = 0;
      protected final int maxRepeatCount;
      protected boolean isRemoved;

      private BaseTask(ResultRun resultRun, int removedTick, int maxRepeatCount) {
        this.resultRun = resultRun;
        this.maxTick = removedTick;
        this.maxRepeatCount = maxRepeatCount;
      }

      @Override
      public void run(DelayTaskHolder delayTaskHolder) {
        if (repeatCount == maxRepeatCount) {
          isRemoved = true;
          return;
        }

        if (tick >= maxTick) {
          resultRun.run(delayTaskHolder.getAttachmentHolder());
          repeatCount++;
          tick = 0;
        }
        tick++;
      }

      @Override
      public boolean isRemoved() {
        return isRemoved;
      }
    }

    class TickTask extends BaseTask {
      private final TickRun tickRun;

      private TickTask(TickRun tickRun, ResultRun resultRun, int removedTick, int maxRepeatCount) {
        super(resultRun, removedTick, maxRepeatCount);
        this.tickRun = tickRun;
      }

      @Override
      public void run(DelayTaskHolder delayTaskHolder) {
        if (repeatCount == maxRepeatCount) {
          isRemoved = true;
          return;
        }

        if (tick >= maxTick) {
          resultRun.run(delayTaskHolder.getAttachmentHolder());
          repeatCount++;
          tick = 0;
        }

        tick = tickRun.run(tick, maxTick, delayTaskHolder.getAttachmentHolder());
      }
    }

    /**
     * 每一tick执行一次可通过修改返回值来自定义结束的时间之类的逻辑
     */
    @FunctionalInterface
    interface TickRun {
      int run(int tick, int maxTick, IAttachmentHolder attachmentHolder);
    }

    @FunctionalInterface
    interface ResultRun {
      void run(IAttachmentHolder attachmentHolder);
    }

    class Builder {
      private @Nullable TickRun tickRun;
      private ResultRun resultRun;
      private int removedTick;
      private int repeatCount = 1;

      private Builder() {
      }

      public static Builder create() {
        return new Builder();
      }

      public Builder tickRun(TickRun tickRun) {
        this.tickRun = tickRun;
        return this;
      }

      public Builder resultRun(ResultRun resultRun) {
        this.resultRun = resultRun;
        return this;
      }

      public Builder removedTick(int removedTick) {
        this.removedTick = removedTick;
        return this;
      }

      public Builder repeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
      }

      public ITask build() {
        assert resultRun != null : "resultRun can not be null";
        assert repeatCount > 0 : "repeatCount can not be less than 1";
        return tickRun == null ?
          new BaseTask(resultRun, removedTick, repeatCount) :
          new TickTask(tickRun, resultRun, removedTick, repeatCount);
      }
    }
  }
}
