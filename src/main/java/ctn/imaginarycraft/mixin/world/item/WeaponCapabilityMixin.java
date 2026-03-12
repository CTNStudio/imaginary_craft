package ctn.imaginarycraft.mixin.world.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.mixed.IWeaponCapability;
import ctn.imaginarycraft.mixed.IWeaponCapability$Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mixin(WeaponCapability.class)
public abstract class WeaponCapabilityMixin extends CapabilityItem implements IWeaponCapability {
  @Shadow
  @Final
  protected Map<Style, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> autoAttackMotions;
  @Unique
  private Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$smashingSoundProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionsProvider;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle;
  @Unique
  private Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers;
  @Unique
  private Function<LivingEntityPatch<?>, Collider> imaginarycraft$colliderProvider;

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

  @Shadow
  public abstract SoundEvent getHitSound();

  @Shadow
  public abstract HitParticleType getHitParticle();

  @Inject(method = "<init>", at = @At("RETURN"))
  private void imaginarycraft$init(
    WeaponCapability.Builder builder,
    CallbackInfo ci,
    @Local(name = "weaponBuilder") WeaponCapability.Builder weaponBuilder
  ) {
    IWeaponCapability$Builder iBuilder = IWeaponCapability$Builder.of(weaponBuilder);
    imaginarycraft$hitParticleProvider = iBuilder.imaginaryCraft$getHitParticleProvider();
    imaginarycraft$smashingSoundProvider = iBuilder.imaginaryCraft$getSwingSoundProvider();
    imaginarycraft$hitSoundProvider = iBuilder.imaginaryCraft$getHitSoundProvider();
    imaginarycraft$autoAttackMotionsProvider = iBuilder.imaginaryCraft$getAutoAttackMotionProviderMap();
    imaginarycraft$innateSkillProviderByStyle = iBuilder.imaginaryCraft$getInnateSkillProviderByStyle();
    imaginarycraft$livingMotionProviderModifiers = iBuilder.imaginaryCraft$getLivingMotionProviderModifiers();
    imaginarycraft$colliderProvider = iBuilder.imaginaryCraft$getColliderProvider();
  }

  @Override
  public HitParticleType imaginaryCraft$getHitParticle(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$hitParticleProvider == null ? getHitParticle() : imaginarycraft$hitParticleProvider.apply(entitypatch);
  }

  @Override
  public SoundEvent imaginaryCraft$getSmashingSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$smashingSoundProvider == null ? getSmashingSound() : imaginarycraft$smashingSoundProvider.apply(entitypatch);
  }

  @Override
  public SoundEvent imaginaryCraft$getHitSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$hitSoundProvider == null ? getHitSound() : imaginarycraft$hitSoundProvider.apply(entitypatch);
  }

  @WrapOperation(method = "getAutoAttackMotion", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object getImaginarycraft$autoAttackMotion(Map<?, ?> instance, Object key, Object defaultValue, Operation<List<?>> original, @Local(name = "playerpatch") PlayerPatch<?> playerpatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return original.call(instance, key, defaultValue);
    }

    var animationAccessors = imaginarycraft$convertToAnimationAccessors(imaginaryCraft$getAutoAttackMotionFunction(playerpatch), playerpatch);
    return animationAccessors != null ? animationAccessors : defaultValue;
  }

  @Override
  public List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getAutoAttackMotionFunction(PlayerPatch<?> playerpatch) {
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
  public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> imaginaryCraft$getMountAttackMotion(LivingEntityPatch<?> entitypatch) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return getMountAttackMotion();
    }
    return imaginarycraft$convertToAnimationAccessors(imaginaryCraft$getMountAttackMotionFunction(entitypatch), entitypatch);
  }

  @Override
  public List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackMotionFunction(LivingEntityPatch<?> entitypatch) {
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

  @Override
  public Collider imaginaryCraft$getWeaponCollider(LivingEntityPatch<?> livingEntityPatch) {
    if (imaginarycraft$colliderProvider == null) {
      return getWeaponCollider();
    }
    Collider collider = imaginarycraft$colliderProvider.apply(livingEntityPatch);
    return collider == null ? getWeaponCollider() : collider;
  }
}
