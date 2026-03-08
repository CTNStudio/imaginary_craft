package ctn.imaginarycraft.mixin.world.item;

import com.google.common.collect.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.particle.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Mixin(WeaponCapability.class)
public abstract class WeaponCapabilityMixin extends CapabilityItem implements IWeaponCapability {
  @Shadow
  @Final
  protected Map<Style, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> autoAttackMotions;
  @Unique
  private Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$swingSoundProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionsProvider;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle;
  @Unique
  private Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers;

  protected WeaponCapabilityMixin(Builder<?> builder) {
    super(builder);
  }

  @Unique
  private static <T, I> List<I> imaginarycraft$convertToAnimationAccessors(List<Function<T, I>> functionList, T t) {
    if (functionList == null) {
      return null;
    }
    List<I> list = new ArrayList<>();
    for (int i = 0, functionListSize = functionList.size(); i < functionListSize; i++) {
      Function<T, I> function = functionList.get(i);
      if (function == null) {
        Function<T, I> tiFunction = (t1) -> null;
        functionList.set(i, tiFunction);
        function = tiFunction;
      }
      I apply = function.apply(t);
      list.add(apply);
    }
    return list;
  }

  @Shadow
  public abstract boolean availableOnHorse();

  @Shadow
  public abstract List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getMountAttackMotion();

  @Shadow
  public abstract List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getAutoAttackMotion(PlayerPatch<?> playerpatch);

  @Inject(method = "<init>", at = @At("RETURN"))
  private void imaginarycraft$init(
    WeaponCapability.Builder builder,
    CallbackInfo ci,
    @Local(name = "weaponBuilder") WeaponCapability.Builder weaponBuilder
  ) {
    IBuilder iBuilder = IBuilder.of(weaponBuilder);
    imaginarycraft$hitParticleProvider = iBuilder.getImaginarycraft$hitParticleProvider();
    imaginarycraft$swingSoundProvider = iBuilder.getImaginarycraft$swingSoundProvider();
    imaginarycraft$hitSoundProvider = iBuilder.getImaginarycraft$hitSoundProvider();
    imaginarycraft$autoAttackMotionsProvider = iBuilder.getImaginarycraft$autoAttackMotionProviderMap();
    imaginarycraft$innateSkillProviderByStyle = iBuilder.getImaginarycraft$innateSkillProviderByStyle();
    imaginarycraft$livingMotionProviderModifiers = iBuilder.getImaginarycraft$livingMotionProviderModifiers();
  }

  @Override
  public HitParticleType getImaginarycraft$hitParticle(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$hitParticleProvider == null ? null : imaginarycraft$hitParticleProvider.apply(entitypatch);
  }

  @Override
  public SoundEvent getImaginarycraft$swingSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$swingSoundProvider == null ? null : imaginarycraft$swingSoundProvider.apply(entitypatch);
  }

  @Override
  public SoundEvent getImaginarycraft$hitSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$hitSoundProvider == null ? null : imaginarycraft$hitSoundProvider.apply(entitypatch);
  }

  @WrapOperation(method = "getAutoAttackMotion", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object getImaginarycraft$autoAttackMotion(Map<?, ?> instance, Object key, Object defaultValue, Operation<List<?>> original, @Local(name = "playerpatch") PlayerPatch<?> playerpatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return original.call(instance, key, defaultValue);
    }

    var animationAccessors = imaginarycraft$convertToAnimationAccessors(getImaginarycraft$autoAttackMotion1(playerpatch), playerpatch);
    return animationAccessors != null ? animationAccessors : defaultValue;
  }

  @Override
  public List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$autoAttackMotion1(PlayerPatch<?> playerpatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return null;
    }

    var function = imaginarycraft$autoAttackMotionsProvider.get(getStyle(playerpatch));
    if (function != null) {
      return function.apply(playerpatch);
    }

    var commonFunction = imaginarycraft$autoAttackMotionsProvider.get(Styles.COMMON);
    if (commonFunction != null) {
      return commonFunction.apply(playerpatch);
    }

    return autoAttackMotions.get(Styles.COMMON).stream()
      .map(animation ->
        (Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>)
          (livingEntityPatch) -> animation).toList();
  }

  @Override
  public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$mountAttackMotion(LivingEntityPatch<?> entitypatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return getMountAttackMotion();
    }
    return imaginarycraft$convertToAnimationAccessors(getImaginarycraft$mountAttackMotion1(entitypatch), entitypatch);
  }

  @Override
  public List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$mountAttackMotion1(LivingEntityPatch<?> entitypatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return new ArrayList<>();
    }

    var mountFunction = imaginarycraft$autoAttackMotionsProvider.get(Styles.MOUNT);
    if (mountFunction != null) {
      return mountFunction.apply(entitypatch);
    }

    return getMountAttackMotion().stream()
      .map(animation ->
        (Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>)
          (livingEntityPatch) -> animation).toList();
  }

  @WrapOperation(method = "availableOnHorse", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"))
  public boolean imaginarycraft$availableOnHorse(
    Map<Style, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> instance,
    Object object,
    Operation<Boolean> original
  ) {
    return original.call(imaginarycraft$autoAttackMotionsProvider == null ? instance : imaginarycraft$autoAttackMotionsProvider, object);
  }

  @WrapOperation(method = "getInnateSkill", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object imaginarycraft$getInnateSkill(
    Map<Style, Function<ItemStack, Skill>> instance,
    Object key,
    Object defaultValue,
    Operation<Function<ItemStack, Skill>> original,
    @Local(name = "playerpatch") PlayerPatch<?> playerpatch
  ) {
    if (imaginarycraft$innateSkillProviderByStyle == null) {
      return original.call(instance, key, defaultValue);
    }
    var livingEntityPatchFunctionFunction = imaginarycraft$innateSkillProviderByStyle.get((Style) key);
    if (livingEntityPatchFunctionFunction == null) {
      return defaultValue;
    }
    return livingEntityPatchFunctionFunction.apply(playerpatch);
  }

  @WrapOperation(method = "getLivingMotionModifier", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object imaginarycraft$getLivingMotionModifier(
    Map<Style, Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> instance,
    Object key,
    Object defaultValue,
    Operation<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> original,
    @Local(name = "player") LivingEntityPatch<?> player
  ) {
    if (imaginarycraft$livingMotionProviderModifiers == null) {
      return original.call(instance, key, defaultValue);
    }

    var functionMap = imaginarycraft$livingMotionProviderModifiers.get((Styles) key);
    if (functionMap == null) {
      return original.call(instance, key, defaultValue);
    }

    return functionMap.entrySet().stream()
      .map((entry) -> Map.entry(entry.getKey(), entry.getValue().apply(player)))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @WrapOperation(method = "getUseAnimation", at = @At(value = "INVOKE", ordinal = 0,
    target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"))
  public boolean imaginarycraft$getUseAnimation(
    Map<Style, Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> instance,
    Object object,
    Operation<Boolean> original
  ) {
    if (imaginarycraft$livingMotionProviderModifiers == null) {
      return original.call(instance, object);
    }
    return original.call(imaginarycraft$livingMotionProviderModifiers, object);
  }

  @WrapOperation(method = "getUseAnimation", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object imaginarycraft$getUseAnimation1(
    Map<Style, Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> instance,
    Object object,
    Operation<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> original,
    @Local(name = "entitypatch") LivingEntityPatch<?> entitypatch
  ) {
    if (imaginarycraft$livingMotionProviderModifiers == null) {
      return original.call(instance, object);
    }

    var functionMap = imaginarycraft$livingMotionProviderModifiers.get((Styles) object);
    if (functionMap == null) {
      return original.call(instance, object);
    }

    return functionMap.entrySet().stream()
      .map((entry) -> Map.entry(entry.getKey(), entry.getValue().apply(entitypatch)))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Mixin(WeaponCapability.Builder.class)
  public abstract static class BuilderMixin extends CapabilityItem.Builder<WeaponCapability.Builder> implements IWeaponCapability.IBuilder {
    @Unique
    private Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider;
    @Unique
    private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$swingSoundProvider;
    @Unique
    private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider;
    @Unique
    private Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionProviderMap;
    @Unique
    private Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle;
    @Unique
    private Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers;

    @Override
    public IBuilder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates) {
      imaginarycraft$hitParticleProvider = ModWeaponTypeReloadListener.getProvider(defaultValue, predicates);
      return this;
    }

    @Override
    public IBuilder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
      imaginarycraft$swingSoundProvider = ModWeaponTypeReloadListener.getProvider(defaultValue, predicates);
      return this;
    }

    @Override
    public IBuilder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
      imaginarycraft$hitSoundProvider = ModWeaponTypeReloadListener.getProvider(defaultValue, predicates);
      return this;
    }

    @Override
    public IBuilder imaginarycraft$newStryleCombo(
      Style style,
      List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates,
      @Nullable List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates
    ) {
      if (imaginarycraft$autoAttackMotionProviderMap == null) {
        imaginarycraft$autoAttackMotionProviderMap = new HashMap<>();
      }
      imaginarycraft$autoAttackMotionProviderMap.put(style, ModWeaponTypeReloadListener.getProvider(defaultPredicates, predicates));
      return this;
    }

    @Override
    public IBuilder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates) {
      if (imaginarycraft$innateSkillProviderByStyle == null) {
        imaginarycraft$innateSkillProviderByStyle = new HashMap<>();
      }
      imaginarycraft$innateSkillProviderByStyle.put(style, ModWeaponTypeReloadListener.getProvider(defaultValue, predicates));
      return this;
    }

    @Override
    public IBuilder imaginarycraft$livingMotionModifier(
      Style style,
      LivingMotion livingMotion,
      AnimationManager.AnimationAccessor<? extends StaticAnimation> defaultValue,
      List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> predicates
    ) {
      if (AnimationManager.checkNull(defaultValue)) {
        ImaginaryCraft.LOGGER.warn("Unable to put an empty animation to weapon capability builder: {}, {}", livingMotion, defaultValue);
        return this;
      }

      if (imaginarycraft$livingMotionProviderModifiers == null) {
        imaginarycraft$livingMotionProviderModifiers = Maps.newHashMap();
      }

      if (!imaginarycraft$livingMotionProviderModifiers.containsKey(style)) {
        imaginarycraft$livingMotionProviderModifiers.put(style, new HashMap<>());
      }

      imaginarycraft$livingMotionProviderModifiers.get(style).put(livingMotion, ModWeaponTypeReloadListener.getProvider(defaultValue, predicates));

      return this;
    }

    @Override
    public Function<LivingEntityPatch<?>, HitParticleType> getImaginarycraft$hitParticleProvider() {
      return imaginarycraft$hitParticleProvider;
    }

    @Override
    public Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$swingSoundProvider() {
      return imaginarycraft$swingSoundProvider;
    }

    @Override
    public Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$hitSoundProvider() {
      return imaginarycraft$hitSoundProvider;
    }

    @Override
    public Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> getImaginarycraft$autoAttackMotionProviderMap() {
      return imaginarycraft$autoAttackMotionProviderMap;
    }

    @Override
    public Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> getImaginarycraft$innateSkillProviderByStyle() {
      return imaginarycraft$innateSkillProviderByStyle;
    }

    @Override
    public Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> getImaginarycraft$livingMotionProviderModifiers() {
      return imaginarycraft$livingMotionProviderModifiers;
    }
  }
}
