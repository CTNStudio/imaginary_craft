package ctn.imaginarycraft.client.animation.player.controller;

import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.MirrorModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
import ctn.imaginarycraft.util.ItemUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 通用动画控制器基类
 */
public class GenericAnimationController {
  private static final Set<Class<? extends GenericAnimationController>> registeredControllers = new HashSet<>();
  protected final Map<AnimationType, List<AnimationNode>> animationNodeMap = new HashMap<>();

  public GenericAnimationController() {
    for (AnimationType type : AnimationType.values()) {
      animationNodeMap.put(type, new ArrayList<>());
    }
  }

  /**
   * 创建基于物品的条件
   */
  @SafeVarargs
  public static @NotNull Condition itemCondition(Supplier<? extends Item>... items) {
    return itemCondition(Arrays.stream(items).map(Supplier::get).toArray(Item[]::new));
  }

  /**
   * 创建基于物品的条件
   */
  @Contract(pure = true)
  public static @NotNull Condition itemCondition(Item... items) {
    return (context, type) -> {
      switch (type) {
        case HANDS -> {
          if (ItemUtil.anyMatchIs(context.mainHandItemStack, items) && ItemUtil.anyMatchIs(context.offHandItemStack, items)) {
            return true;
          }
        }
        case MAIN_HAND -> {
          if (ItemUtil.anyMatchIs(context.mainHandItemStack, items)) {
            return true;
          }
        }
        case OFF_HAND -> {
          if (ItemUtil.anyMatchIs(context.offHandItemStack, items)) {
            return true;
          }
        }
        case HAND -> {
          if (ItemUtil.anyMatchIs(context.mainHandItemStack, items) || ItemUtil.anyMatchIs(context.offHandItemStack, items)) {
            return true;
          }
        }
      }
      return false;
    };
  }

  /**
   * 创建基于物品堆栈谓词的条件
   */
  @Contract(pure = true)
  public static @NotNull Condition itemStackCondition(Predicate<ItemStack> itemStackPredicate) {
    return (context, type) -> {
      switch (type) {
        case HANDS -> {
          if (itemStackPredicate.test(context.mainHandItemStack) && itemStackPredicate.test(context.offHandItemStack)) {
            return true;
          }
        }
        case MAIN_HAND -> {
          if (itemStackPredicate.test(context.mainHandItemStack)) {
            return true;
          }
        }
        case OFF_HAND -> {
          if (itemStackPredicate.test(context.offHandItemStack)) {
            return true;
          }
        }
        case HAND -> {
          if (itemStackPredicate.test(context.mainHandItemStack)) {
            return true;
          } else if (itemStackPredicate.test(context.offHandItemStack)) {
            context.controller.addModifierLast(new MirrorModifier());
            return true;
          }
        }
      }
      return false;
    };
  }

  /**
   * 创建基于玩家移动状态的条件
   */
  @Contract(pure = true)
  public static @NotNull Condition movementCondition(Predicate<Double> speedPredicate) {
    return (context, type) -> {
      double speed = Math.sqrt(
        Math.pow(context.player.getX() - context.player.xo, 2) +
          Math.pow(context.player.getZ() - context.player.zo, 2)
      );
      return speedPredicate.test(speed);
    };
  }

  /**
   * 创建基于玩家是否在空中或地面的条件
   */
  @Contract(pure = true)
  public static @NotNull Condition groundCondition(boolean onGround) {
    return (context, type) -> context.player.onGround() == onGround;
  }

  /**
   * 创建复合条件
   */
  @Contract(pure = true)
  public static @NotNull Condition compositeCondition(Condition... conditions) {
    return (context, type) -> Arrays.stream(conditions).allMatch(cond -> cond.test(context, type));
  }

  /**
   * 创建任一条件满足的复合条件
   */
  @Contract(pure = true)
  public static @NotNull Condition anyCondition(Condition... conditions) {
    return (context, type) -> Arrays.stream(conditions).anyMatch(cond -> cond.test(context, type));
  }

  /**
   * 创建过度动画动作
   */
  public static @NotNull Action excessiveAction(ResourceLocation animationId) {
    return (context) -> {
      if (PlayerAnimationUtil.isPlaying(context.controller, animationId)) {
        context.controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), animationId, true);
        return PlayState.STOP;
      }
      return PlayState.CONTINUE;
    };
  }

  /**
   * 创建基础动画动作
   */
  public static @NotNull Action basicAction(ResourceLocation animationId) {
    return (context) -> {
      if (PlayerAnimationUtil.isPlaying(context.controller, animationId)) {
        context.controller.triggerAnimation(animationId);
        return PlayState.STOP;
      }
      return PlayState.CONTINUE;
    };
  }

  /**
   * 创建触发动画动作
   */
  public static @NotNull Action triggerAction(ResourceLocation animationId) {
    return (context) -> {
      context.controller.triggerAnimation(animationId);
      return PlayState.CONTINUE;
    };
  }

  /**
   * 创建替换动画动作
   */
  public static @NotNull Action replaceAction(ResourceLocation animationId) {
    return (context) -> {
      if (!PlayerAnimationUtil.isPlaying(context.controller, animationId)) {
        context.controller.triggerAnimation(animationId);
      }
      return PlayState.CONTINUE;
    };
  }

  /**
   * 创建带淡入效果的替换动画动作
   */
  public static @NotNull Action fadeInReplaceAction(ResourceLocation animationId, int fadeTicks) {
    return (context) -> {
      if (!PlayerAnimationUtil.isPlaying(context.controller, animationId)) {
        context.controller.replaceAnimationWithFade(
          AbstractFadeModifier.standardFadeIn(fadeTicks, EasingType.EASE_IN_OUT_SINE),
          animationId,
          true
        );
      }
      return PlayState.CONTINUE;
    };
  }

  /**
   * 创建停止动画动作
   */
  public static @NotNull Action stopAction() {
    return (context) -> {
      context.controller.stopTriggeredAnimation();
      return PlayState.STOP;
    };
  }

  /**
   * 创建无操作动作
   */
  public static @NotNull Action noopAction() {
    return (context) -> PlayState.CONTINUE;
  }

  /**
   * 注册双手动画操作
   */
  public static <T extends GenericAnimationController> @NotNull T registerHands(@NotNull T instance, Condition condition, Action action) {
    return registerOperation(instance, AnimationType.HANDS, condition, action);
  }

  /**
   * 注册动画操作
   */
  @Contract("_, _, _, _ -> param1")
  public static <T extends GenericAnimationController> @NotNull T registerOperation(@NotNull T instance, AnimationType type, Condition condition, Action action) {
    instance.animationNodeMap.computeIfAbsent(type, (a) -> new ArrayList<>()).add(new AnimationNode(condition, action));
    return instance;
  }

  /**
   * 注册主手动画操作
   */
  public static <T extends GenericAnimationController> @NotNull T registerMainHand(@NotNull T instance, Condition condition, Action action) {
    return registerOperation(instance, AnimationType.MAIN_HAND, condition, action);
  }

  /**
   * 注册副手动画操作
   */
  public static <T extends GenericAnimationController> @NotNull T registerOffHand(@NotNull T instance, Condition condition, Action action) {
    return registerOperation(instance, AnimationType.OFF_HAND, condition, action);
  }

  /**
   * 注册任意手动画操作
   */
  public static <T extends GenericAnimationController> @NotNull T registerHand(@NotNull T instance, Condition condition, Action action) {
    return registerOperation(instance, AnimationType.HAND, condition, action);
  }

  /**
   * 链式注册双手动画操作
   */
  public @NotNull GenericAnimationController hands(Condition condition, Action action) {
    return registerOperation(this, AnimationType.HANDS, condition, action);
  }

  /**
   * 链式注册主手动画操作
   */
  public @NotNull GenericAnimationController mainHand(Condition condition, Action action) {
    return registerOperation(this, AnimationType.MAIN_HAND, condition, action);
  }

  /**
   * 链式注册副手动画操作
   */
  public @NotNull GenericAnimationController offHand(Condition condition, Action action) {
    return registerOperation(this, AnimationType.OFF_HAND, condition, action);
  }

  /**
   * 链式注册任意手动画操作
   */
  public @NotNull GenericAnimationController hand(Condition condition, Action action) {
    return registerOperation(this, AnimationType.HAND, condition, action);
  }

  /**
   * 批量注册动画操作
   */
  @SafeVarargs
  public final @NotNull GenericAnimationController batchRegister(AnimationRegistration... registrations) {
    for (AnimationRegistration registration : registrations) {
      registerOperation(this, registration.type, registration.condition, registration.action);
    }
    return this;
  }

  public PlayState execute(ModPlayerAnimationController controller, AnimationData data, AnimationController.AnimationSetter setter) {
    AnimationContext context = new AnimationContext(controller, data, setter);
    controller.removeModifierIf(MirrorModifier.class::isInstance);
    for (AnimationType type : AnimationType.values()) {
      for (AnimationNode node : animationNodeMap.get(type)) {
        Map.Entry<Boolean, PlayState> result = node.execute(context, type);
        if (result.getKey()) {
          PlayState value = result.getValue();
          if (value == PlayState.STOP) {
            controller.stopTriggeredAnimation();
          }
          return value;
        }
      }
    }
    controller.stopTriggeredAnimation();
    return PlayState.STOP;
  }

  /**
   * 动画类型枚举
   */
  public enum AnimationType {
    HANDS,
    MAIN_HAND,
    OFF_HAND,
    HAND
  }

  /**
   * 动作函数式接口
   */
  @FunctionalInterface
  public interface Action {
    default Action andThen(Action nextAction) {
      return (context) -> {
        PlayState firstResult = this.execute(context);
        // 只有当前一个动作返回CONTINUE时，才执行下一个动作
        if (firstResult == PlayState.CONTINUE) {
          return nextAction.execute(context);
        }
        return firstResult;
      };
    }

    PlayState execute(AnimationContext context);

    default Action orElse(Action alternativeAction) {
      return (context) -> {
        PlayState result = this.execute(context);
        // 如果当前动作返回STOP或其它非CONTINUE状态，则执行替代动作
        if (result != PlayState.CONTINUE) {
          return alternativeAction.execute(context);
        }
        return result;
      };
    }

    default Action always(Action nextAction) {
      return (context) -> {
        this.execute(context);  // 先执行当前动作
        return nextAction.execute(context);  // 然后执行下一个动作，忽略第一个动作的结果
      };
    }

    default Action invert() {
      return (context) -> {
        PlayState result = this.execute(context);
        // 反转结果：如果原来是CONTINUE则返回STOP，如果是STOP则返回CONTINUE
        return result == PlayState.CONTINUE ? PlayState.STOP : PlayState.CONTINUE;
      };
    }
  }

  /**
   * 条件函数式接口，支持链式调用
   */
  @FunctionalInterface
  public interface Condition {
    default Condition and(Condition other) {
      return (context, type) -> this.test(context, type) && other.test(context, type);
    }

    boolean test(AnimationContext context, AnimationType type);

    default Condition or(Condition other) {
      return (context, type) -> this.test(context, type) || other.test(context, type);
    }

    default Condition negate() {
      return (context, type) -> !this.test(context, type);
    }
  }

  /**
   * 动画注册项类
   */
  public static class AnimationRegistration {
    public final AnimationType type;
    public final Condition condition;
    public final Action action;

    public AnimationRegistration(AnimationType type, Condition condition, Action action) {
      this.type = type;
      this.condition = condition;
      this.action = action;
    }

    public static AnimationRegistration of(AnimationType type, Condition condition, Action action) {
      return new AnimationRegistration(type, condition, action);
    }
  }

  /**
   * 动画节点，包含条件和动作
   */
  public static class AnimationNode {
    private final Condition condition;
    private final Action action;

    public AnimationNode(Condition condition, Action action) {
      this.condition = condition;
      this.action = action;
    }

    public Map.Entry<Boolean, PlayState> execute(AnimationContext context, AnimationType type) {
      return condition.test(context, type) ? Map.entry(true, action.execute(context)) : Map.entry(false, null);
    }
  }

  /**
   * 动画上下文类
   */
  public static class AnimationContext {
    public final ModPlayerAnimationController controller;
    public final AbstractClientPlayer player;
    public final ItemStack mainHandItemStack;
    public final Item mainHandItem;
    public final ItemStack offHandItemStack;
    public final Item offHandItem;
    public final AnimationData data;
    public final AnimationController.AnimationSetter setter;

    public AnimationContext(ModPlayerAnimationController controller, AnimationData data, AnimationController.AnimationSetter setter) {
      this.controller = controller;
      this.player = controller.getPlayer();
      this.mainHandItemStack = player.getMainHandItem();
      this.mainHandItem = mainHandItemStack.getItem();
      this.offHandItemStack = player.getOffhandItem();
      this.offHandItem = offHandItemStack.getItem();
      this.data = data;
      this.setter = setter;
    }
  }
}
