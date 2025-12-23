
package ctn.imaginarycraft.api.client.playeranimcore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.enums.AnimationStage;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 玩家动画原始动画类，用于构建和管理玩家动画链
 * 提供了创建和操作动画序列的方法，支持多种动画播放模式
 */
public class PlayerAnimRawAnimation {
  /**
   * 动画阶段列表，存储构成完整动画序列的所有动画阶段
   */
  private final List<PlayerAnimStage> animationList;

  /**
   * 用于序列化和反序列化PlayerAnimRawAnimation对象的编解码器
   */
  public static final Codec<PlayerAnimRawAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    PlayerAnimStage.CODEC.listOf().fieldOf("playerAnimStageLis").forGetter(PlayerAnimRawAnimation::getAnimationStages)
  ).apply(instance, PlayerAnimRawAnimation::new));

  /**
   * 用于流式序列化和反序列化PlayerAnimRawAnimation对象的编解码器
   */
  public static final StreamCodec<ByteBuf, PlayerAnimRawAnimation> STREAM_CODEC = StreamCodec.composite(
    PlayerAnimStage.STREAM_CODEC.apply(ByteBufCodecs.list()), PlayerAnimRawAnimation::getAnimationStages,
    PlayerAnimRawAnimation::new);

  /**
   * 私有构造函数，强制使用工厂方法进行逻辑操作
   * 初始化空的动画列表
   */
  private PlayerAnimRawAnimation() {
    this.animationList = new ObjectArrayList<>();
  }

  /**
   * 使用指定动画列表初始化动画对象的私有构造函数
   *
   * @param animationList 动画阶段列表
   */
  private PlayerAnimRawAnimation(List<PlayerAnimStage> animationList) {
    this.animationList = animationList;
  }

  /**
   * 开始一个新的RawAnimation实例。这是创建动画链的起点
   *
   * @return 一个新的RawAnimation实例
   */
  public static PlayerAnimRawAnimation begin() {
    return new PlayerAnimRawAnimation();
  }

  /**
   * 将动画ID追加到动画ID链中，播放指定的动画ID，然后根据动画JSON中设置的循环类型
   * 停止或继续到下一个链式动画ID
   *
   * @param animationId 要播放一次的动画ID
   */
  public PlayerAnimRawAnimation thenPlay(ResourceLocation animationId) {
    return then(animationId, Animation.LoopType.DEFAULT);
  }

  /**
   * 将动画ID追加到动画ID链中，播放指定的动画ID并持续重复播放，直到动画ID被外部源停止
   *
   * @param animationId 要循环播放的动画ID
   */
  public PlayerAnimRawAnimation thenLoop(ResourceLocation animationId) {
    return then(animationId, Animation.LoopType.LOOP);
  }

  /**
   * 将'等待'动画追加到动画链中
   * <p>
   * 这会使动画对象在执行下一个动画之前的一段时间内不执行任何操作
   *
   * @param ticks 要'等待'的刻度数
   */
  public PlayerAnimRawAnimation thenWait(int ticks) {
    this.animationList.add(new PlayerAnimStage(AnimationStage.WAIT, null, Animation.LoopType.PLAY_ONCE, ticks));

    return this;
  }

  /**
   * 将动画ID追加到动画ID链中，然后让动画对象在动画ID结束时保持姿势
   * 直到它被外部源停止
   *
   * @param animationId 要播放并保持的动画ID
   */
  public PlayerAnimRawAnimation thenPlayAndHold(ResourceLocation animationId) {
    return then(animationId, Animation.LoopType.HOLD_ON_LAST_FRAME);
  }

  /**
   * 将动画ID追加到动画ID链中，播放指定的动画ID <code>playCount</code> 次，
   * 然后根据动画JSON中设置的循环类型停止或继续到下一个链式动画ID
   *
   * @param animationId 要播放X次的动画ID
   * @param playCount   在继续之前重复动画ID的次数
   */
  public PlayerAnimRawAnimation thenPlayXTimes(ResourceLocation animationId, int playCount) {
    for (int i = 0; i < playCount; i++) {
      then(animationId, i == playCount - 1 ? Animation.LoopType.DEFAULT : Animation.LoopType.PLAY_ONCE);
    }

    return this;
  }

  /**
   * 将动画追加到动画链中，播放指定的动画并根据提供的<code>loopType</code>参数继续
   *
   * @param animationId 要播放的动画。<u>必须</u>与<code>.animation.json</code>文件中的动画名称匹配。
   * @param loopType    动画的循环类型处理器，覆盖动画JSON中设置的默认值
   */
  public PlayerAnimRawAnimation then(ResourceLocation animationId, Animation.LoopType loopType) {
    this.animationList.add(new PlayerAnimStage(animationId, loopType));

    return this;
  }

  /**
   * 获取动画阶段列表
   *
   * @return 当前动画的所有阶段列表
   */
  public List<PlayerAnimStage> getAnimationStages() {
    return this.animationList;
  }

  /**
   * 基于现有的RawAnimation实例创建一个新的RawAnimation实例
   * <p>
   * 新实例将是另一个实例的浅拷贝，然后可以追加或以其他方式修改
   *
   * @param other 要复制的现有RawAnimation实例
   * @return 一个新的RawAnimation实例
   */
  public static PlayerAnimRawAnimation copyOf(PlayerAnimRawAnimation other) {
    PlayerAnimRawAnimation newInstance = PlayerAnimRawAnimation.begin();

    newInstance.animationList.addAll(other.animationList);

    return newInstance;
  }

  /**
   * 比较两个PlayerAnimRawAnimation对象是否相等
   *
   * @param obj 要比较的对象
   * @return 如果两个对象相等则返回true，否则返回false
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null || getClass() != obj.getClass())
      return false;

    return hashCode() == obj.hashCode();
  }

  /**
   * 计算当前对象的哈希码
   *
   * @return 基于动画列表计算的哈希码
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.animationList);
  }

  /**
   * 玩家动画阶段记录类，表示动画序列中的单个阶段
   * 包含动画阶段类型、动画ID、循环类型名称和附加刻度数
   */
  public record PlayerAnimStage(AnimationStage stage,
                                Optional<@Nullable ResourceLocation> animationId,
                                String loopTypeName, int additionalTicks) {
    /**
     * 用于序列化和反序列化AnimationStage的编解码器
     */
    public static final Codec<AnimationStage> ANIMATION_STAGE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
      Codec.STRING.fieldOf("name").forGetter(AnimationStage::name)
    ).apply(instance, AnimationStage::valueOf));

    /**
     * 用于流式序列化和反序列化AnimationStage的编解码器
     */
    public static final StreamCodec<ByteBuf, AnimationStage> ANIMATION_STAGE_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, AnimationStage::name,
      AnimationStage::valueOf);

    /**
     * 用于序列化和反序列化Animation.LoopType的编解码器
     */
    public static final Codec<Animation.LoopType> LOOP_TYPE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
      Codec.STRING.fieldOf("name").forGetter(PlayerAnimStage::getLoopTypeName)
    ).apply(instance, PlayerAnimStage::getLoopType));

    /**
     * 用于流式序列化和反序列化Animation.LoopType的编解码器
     */
    public static final StreamCodec<ByteBuf, Animation.LoopType> LOOP_TYPE_STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, PlayerAnimStage::getLoopTypeName,
      PlayerAnimStage::getLoopType);

    /**
     * 用于序列化和反序列化PlayerAnimStage对象的编解码器
     */
    public static final Codec<PlayerAnimStage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
      ANIMATION_STAGE_CODEC.fieldOf("stage").forGetter(PlayerAnimStage::stage),
      ResourceLocation.CODEC.optionalFieldOf("animationId").forGetter(PlayerAnimStage::animationId),
      Codec.STRING.fieldOf("loopTypeName").forGetter(PlayerAnimStage::loopTypeName),
      Codec.INT.fieldOf("additionalTicks").forGetter(PlayerAnimStage::additionalTicks)
    ).apply(instance, PlayerAnimStage::new));

    /**
     * 用于流式序列化和反序列化PlayerAnimStage对象的编解码器
     */
    public static final StreamCodec<ByteBuf, PlayerAnimStage> STREAM_CODEC = StreamCodec.composite(
      ANIMATION_STAGE_STREAM_CODEC, PlayerAnimStage::stage,
      ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), PlayerAnimStage::animationId,
      ByteBufCodecs.STRING_UTF8, PlayerAnimStage::loopTypeName,
      ByteBufCodecs.INT, PlayerAnimStage::additionalTicks,
      PlayerAnimStage::new);

    /**
     * 使用阶段、动画ID、循环类型名称和附加刻度数初始化PlayerAnimStage
     *
     * @param stage           动画阶段
     * @param animationId     动画资源位置，可为null
     * @param loopTypeName    循环类型名称
     * @param additionalTicks 附加刻度数
     */
    public PlayerAnimStage(AnimationStage stage, @Nullable ResourceLocation animationId, String loopTypeName, int additionalTicks) {
      this(stage, Optional.ofNullable(animationId), loopTypeName, additionalTicks);
    }

    /**
     * 使用阶段、动画ID、循环类型和附加刻度数初始化PlayerAnimStage
     *
     * @param stage           动画阶段
     * @param animationId     动画资源位置，可为null
     * @param loopType        循环类型
     * @param additionalTicks 附加刻度数
     */
    public PlayerAnimStage(AnimationStage stage, @Nullable ResourceLocation animationId, Animation.LoopType loopType, int additionalTicks) {
      this(stage, animationId, getLoopTypeName(loopType), additionalTicks);
    }

    /**
     * 使用阶段、动画ID和循环类型初始化PlayerAnimStage
     *
     * @param stage       动画阶段
     * @param animationId 动画资源位置，可为null
     * @param loopType    循环类型
     */
    public PlayerAnimStage(AnimationStage stage, @Nullable ResourceLocation animationId, Animation.LoopType loopType) {
      this(stage, animationId, loopType, 0);
    }

    /**
     * 使用动画ID和循环类型初始化PlayerAnimStage
     *
     * @param animationId 动画资源位置，可为null
     * @param loopType    循环类型
     */
    public PlayerAnimStage(@Nullable ResourceLocation animationId, Animation.LoopType loopType) {
      this(AnimationStage.ANIMATION, animationId, loopType);
    }

    /**
     * 使用阶段、动画ID和循环类型名称初始化PlayerAnimStage
     *
     * @param stage        动画阶段
     * @param animationId  动画资源位置，可为null
     * @param loopTypeName 循环类型名称
     */
    public PlayerAnimStage(AnimationStage stage, @Nullable ResourceLocation animationId, String loopTypeName) {
      this(stage, animationId, loopTypeName, 0);
    }

    /**
     * 使用动画ID和循环类型名称初始化PlayerAnimStage
     *
     * @param animationId  动画资源位置，可为null
     * @param loopTypeName 循环类型名称
     */
    public PlayerAnimStage(@Nullable ResourceLocation animationId, String loopTypeName) {
      this(AnimationStage.ANIMATION, animationId, loopTypeName);
    }

    /**
     * 将PlayerAnimStage转换为RawAnimation.Stage对象
     *
     * @return 转换后的RawAnimation.Stage对象
     */
    public RawAnimation.Stage toAnimationStage() {
      return new RawAnimation.Stage(stage, animationId.map(PlayerAnimResources::getAnimation).orElse(null), getLoopType(loopTypeName), additionalTicks);
    }

    /**
     * 根据循环类型名称获取对应的Animation.LoopType枚举值
     *
     * @param loopTypeName 循环类型名称
     * @return 对应的Animation.LoopType枚举值
     */
    public static Animation.LoopType getLoopType(String loopTypeName) {
      if (loopTypeName.equals("default")) {
        return Animation.LoopType.DEFAULT;
      }
      return Animation.LoopType.LOOP_TYPES.getOrDefault(loopTypeName, Animation.LoopType.DEFAULT);
    }

    /**
     * 根据Animation.LoopType枚举值获取对应的循环类型名称
     *
     * @param loopType Animation.LoopType枚举值
     * @return 对应的循环类型名称
     */
    public static @NotNull String getLoopTypeName(Animation.LoopType loopType) {
      return Animation.LoopType.LOOP_TYPES.entrySet().stream()
        .filter(entry -> entry.getValue().equals(loopType))
        .findFirst().map(Map.Entry::getKey).orElse("default");
    }

    /**
     * 比较两个PlayerAnimStage对象是否相等
     *
     * @param obj 要比较的对象
     * @return 如果两个对象相等则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;

      if (obj == null || getClass() != obj.getClass())
        return false;

      return hashCode() == obj.hashCode();
    }

    /**
     * 计算当前对象的哈希码
     *
     * @return 基于阶段、动画ID和循环类型名称计算的哈希码
     */
    @Override
    public int hashCode() {
      return Objects.hash(this.stage, this.animationId, this.loopTypeName);
    }
  }

  /**
   * 将PlayerAnimRawAnimation转换为RawAnimation对象
   *
   * @return 转换后的RawAnimation对象
   */
  public RawAnimation toRawAnimation() {
    RawAnimation begin = RawAnimation.begin();
    begin.getAnimationStages().addAll(this.animationList.stream().map(PlayerAnimStage::toAnimationStage).toList());
    return begin;
  }
}
