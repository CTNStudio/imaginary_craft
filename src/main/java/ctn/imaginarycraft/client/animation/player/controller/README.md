# 通用动画控制器系统使用指南

## 概述

通用动画控制器系统是一个灵活、可扩展的动画管理框架，用于处理玩家角色的各种动画状态。它基于节点概念设计，支持条件判断和动作执行，允许开发者轻松定义复杂动画逻辑。系统使用统一的注册机制，确保每个控制器只在首次实例化时进行注册。

## 核心组件

### 1. AnimationType（动画类型）
- `HANDS`: 双手持握动画
- `MAIN_HAND`: 主手动画
- `OFF_HAND`: 副手动画
- `HAND`: 任意手动画

## 统一注册机制

### 1. 自动注册
每个继承自 GenericAnimationController 的子类都会在首次实例化时自动注册，确保初始化逻辑只执行一次。

### 2. 首次注册回调
子类可以通过重写 `onFirstTimeRegistration` 静态方法来定义仅在首次注册时执行的初始化逻辑。

### 2. Condition（条件接口）
条件接口支持链式调用：
- `and()`: 逻辑与操作
- `or()`: 逻辑或操作
- `negate()`: 逻辑非操作

### 3. Action（动作接口）
动作接口定义了动画执行逻辑，支持：
- `andThen()`: 顺序执行多个动作
- `orElse()`: 备选动作执行
- `always()`: 总是执行后续动作
- `invert()`: 反转执行结果

### 4. AnimationNode（动画节点）
封装条件和动作的组合单元。

## 使用方法

### 基础注册方式

```java
public class MyAnimationController extends GenericAnimationController {
    public static final MyAnimationController INSTANCE = new MyAnimationController();

    private MyAnimationController() {
        super();
    }

    protected static void onFirstTimeRegistration(Class<? extends GenericAnimationController> clazz) {
        if (clazz == MyAnimationController.class) {
            registerHands(INSTANCE,
                itemCondition(MyItems.MY_WEAPON),
                myAnimationCollection::executeAnim);
        }
    }
}
```

### 链式调用方式（推荐）

```java
public class MyAnimationController extends GenericAnimationController {
    public static final MyAnimationController INSTANCE = new MyAnimationController();

    private MyAnimationController() {
        super();
    }

    protected static void onFirstTimeRegistration(Class<? extends GenericAnimationController> clazz) {
        if (clazz == MyAnimationController.class) {
            INSTANCE
                .hands(
                    itemCondition(MyItems.MY_WEAPON),
                    myAnimationCollection::executeAnim
                )
                .mainHand(
                    itemCondition(MyItems.OTHER_ITEM),
                    otherAnimationCollection::executeAnim
                );
        }
    }
}
```

### 复合条件

```java
// 组合多个条件
INSTANCE.hands(
    itemCondition(MyItems.MY_WEAPON)
        .and(groundCondition(true))
        .or(movementCondition(speed -> speed > 0.1)),
    myAnimationCollection::executeAnim
);
```

### 批量注册

```java
INSTANCE.batchRegister(
    AnimationRegistration.of(
        AnimationType.MAIN_HAND,
        itemCondition(MyItems.ITEM1),
        action1
    ),
    AnimationRegistration.of(
        AnimationType.HAND,
        itemCondition(MyItems.ITEM2),
        action2
    )
);
```

## 预定义条件

### 物品相关
- `itemCondition(Item...)`: 检查特定物品
- `itemStackCondition(Predicate<ItemStack>)`: 使用谓词检查物品堆栈
- `compositeCondition(Condition...)`: 多个条件同时满足
- `anyCondition(Condition...)`: 任一条件满足

### 状态相关
- `movementCondition(Predicate<Double>)`: 检查移动速度
- `groundCondition(boolean)`: 检查是否在地面
- `sneakingCondition(boolean)`: 检查潜行状态
- `swimmingCondition(boolean)`: 检查游泳状态

## 预定义动作

- `basicAction(ResourceLocation)`: 基础动画触发
- `excessiveAction(ResourceLocation)`: 过度动画（带淡入效果）
- `triggerAction(ResourceLocation)`: 触发动画
- `replaceAction(ResourceLocation)`: 替换动画
- `fadeInReplaceAction(ResourceLocation, int)`: 带淡入效果的替换动画
- `stopAction()`: 停止动画
- `noopAction()`: 空操作

## 高级用法

### 自定义条件
```java
Condition customCondition = (context, type) -> {
    // 自定义逻辑
    return context.player.tickCount % 100 == 0; // 每100tick执行一次
};
```

### 自定义动作
```java
Action customAction = (context) -> {
    // 自定义动画逻辑
    context.controller.triggerAnimation(myAnimationId);
    return PlayState.CONTINUE;
};
```

### 辅助工具类
使用 `AnimationControllerHelper` 提供的便捷方法：

```java
// 创建简单的物品条件
Condition simpleCondition = AnimationControllerHelper.simpleItemCondition(MyItem);

// 创建NBT条件
Condition nbtCondition = AnimationControllerHelper.nbtCondition(stack -> 
    stack.hasTag() && stack.getTag().getBoolean("special_property"));

// 组合多个动作
Action combinedAction = AnimationControllerHelper.combineActions(
    firstAction,
    secondAction,
    thirdAction
);

// 基于血量百分比的条件
Condition healthCondition = AnimationControllerHelper.healthPercentageCondition(0.5, 1.0);
```

## 统一注册管理器

使用 `AnimationControllerRegistry` 来统一管理和执行所有动画控制器：

```java
// 执行所有注册的控制器
PlayState result = AnimationControllerRegistry.executeAll(controller, data, setter);

// 执行特定类型的控制器
PlayState result = AnimationControllerRegistry.executeByType(
    controller, 
    data, 
    setter, 
    MyAnimationController.class
);
```

## 最佳实践

1. **使用链式调用**: 推荐使用 `.hands()`, `.mainHand()`, `.offHand()`, `.hand()` 方法进行链式注册。
2. **合理组织条件**: 使用 `and()`, `or()` 等方法组合条件，提高可读性。
3. **复用条件**: 将常用条件定义为常量或静态方法。
4. **性能考虑**: 避免在条件中使用过于复杂的计算逻辑。
5. **错误处理**: 在自定义动作中正确处理异常情况。
6. **统一注册**: 将初始化逻辑放在 `onFirstTimeRegistration` 方法中，确保只执行一次。

通过以上改进，动画控制器系统更加模块化、易于使用，并且提供了更多的灵活性和扩展性。