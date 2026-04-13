package ctn.imaginarycraft.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.mixed.IMoveSet;
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
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
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
	/**
	 * 对应 hitParticle
	 */
	@Unique
  private Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider;
	/**
	 * 对应 smashingSound
	 */
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$smashingSoundProvider;
	/**
	 * 对应 hitSound
	 */
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider;
	/**
	 * 对应 autoAttackMotions
	 */
  @Unique
  @Deprecated
  private Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionsProvider;
	/**
	 * 对应 innateSkill
	 */
  @Unique
  @Deprecated
  private Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle;
	/**
	 * 对应 livingMotionModifiers
	 */
  @Unique
  @Deprecated
  private Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers;
	/**
	 * 对应 CapabilityItem.collider
	 */
  @Unique
  @Deprecated
  private Function<LivingEntityPatch<?>, Collider> imaginarycraft$colliderProvider;

  protected WeaponCapabilityMixin(Builder<?> builder) {
    super(builder);
  }

  @Shadow
  public abstract boolean availableOnHorse();

  @Shadow
  public abstract List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getAutoAttackMotion(PlayerPatch<?> playerpatch);

  @Inject(method = "<init>", at = @At("RETURN"))
  private void imaginarycraft$init(
    WeaponCapability.Builder builder,
    CallbackInfo ci
  ) {
	  IWeaponCapability$Builder iBuilder = IWeaponCapability$Builder.of(builder);
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

  @Shadow
  public abstract HitParticleType getHitParticle();

  @Override
  public SoundEvent imaginaryCraft$getSmashingSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$smashingSoundProvider == null ? getSmashingSound() : imaginarycraft$smashingSoundProvider.apply(entitypatch);
  }

  @Override
  public SoundEvent imaginaryCraft$getHitSound(LivingEntityPatch<?> entitypatch) {
    return imaginarycraft$hitSoundProvider == null ? getHitSound() : imaginarycraft$hitSoundProvider.apply(entitypatch);
  }

  @Shadow
  public abstract SoundEvent getHitSound();

  @WrapOperation(method = "getAutoAttackMotion", at = @At(value = "INVOKE",
    target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object getImaginarycraft$autoAttackMotion(
	  Map<?, ?> instance,
	  Object key,
	  Object defaultValue,
	  Operation<List<?>> original,
	  @Local(name = "playerpatch") PlayerPatch<?> playerpatch
  ) {
    if (imaginarycraft$autoAttackMotionsProvider == null) {
      return original.call(instance, key, defaultValue);
    }

    var animationAccessors = imaginarycraft$convertToAnimationAccessors(imaginaryCraft$getAutoAttackMotionFunction(playerpatch), playerpatch);
    return animationAccessors != null ? animationAccessors : defaultValue;
  }

	@WrapOperation(method = "getAutoAttackMotion", at = @At(value = "INVOKE",
		target = "Lyesman/epicfight/api/ex_cap/modules/core/data/MoveSet;getComboAttackAnimations()Ljava/util/List;"))
	public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$autoAttackMotion1(
		MoveSet instance,
		Operation<List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> original,
		@Local(name = "playerpatch") PlayerPatch<?> playerpatch
	) {
		var function = IMoveSet.of(instance).imaginaryCraft$getImaginarycraft$comboAttackAnimationsProvider();
		if (function == null) {
			return original.call(instance);
		}

		return function.apply(playerpatch);
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

  @Shadow
  public abstract List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getMountAttackMotion();

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

	@WrapOperation(method = "getCurrentSet", at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
	public Object imaginarycraft$getCurrentSet(Map<Style, MoveSet> instance, Object key, Object defaultValue, Operation<MoveSet> original) {
		return null;
	}

	@WrapOperation(method = "availableOnHorse(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;)Z", at = @At(value = "INVOKE",
		target = "Lyesman/epicfight/api/ex_cap/modules/core/data/MoveSet;getMountAttackAnimations()Ljava/util/List;"))
	public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> imaginarycraft$availableOnHorse(
		MoveSet instance,
		Operation<List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> original,
		@Local(name = "entityPatch") LivingEntityPatch<?> entityPatch
	) {
		var function = IMoveSet.of(instance).imaginaryCraft$getMountAttackAnimationsProvider();
		return function != null ? function.apply(entityPatch) : original.call(instance);
	}

	@WrapOperation(method = "availableOnHorse(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;)Z", at = @At(value = "INVOKE",
		target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability;availableOnHorse()Z"))
	public boolean imaginarycraft$availableOnHorse1(
		WeaponCapability instance,
		Operation<Boolean> original
	) {
		if (imaginarycraft$autoAttackMotionsProvider == null) {
			return original.call(instance);
		}
		return imaginarycraft$autoAttackMotionsProvider.containsKey(Styles.MOUNT);
	}

  @WrapOperation(method = "getInnateSkill", at = @At(value = "INVOKE",
	  target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
  public Object imaginarycraft$getInnateSkill(
	  Map<Style, Function<ItemStack, Skill>> instance,
	  Object key,
	  Operation<Object> original,
	  @Local(name = "playerpatch") PlayerPatch<?> playerPatch
  ) {
    if (imaginarycraft$innateSkillProviderByStyle == null) {
	    return original.call(instance, key);
    }
	  var function = imaginarycraft$innateSkillProviderByStyle.get((Style) key);
	  return function == null ? null : function.apply(playerPatch);
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

	@WrapOperation(method = "getLivingMotionModifier", at = @At(value = "INVOKE",
		target = "Lyesman/epicfight/api/ex_cap/modules/core/data/MoveSet;getLivingMotionModifiers()Ljava/util/Map;"))
	public Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> imaginarycraft$getLivingMotionModifier1(
		MoveSet instance,
		Operation<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> original,
		@Local(name = "player") LivingEntityPatch<?> player
	) {
		var map = IMoveSet.of(instance).imaginaryCraft$getLivingMotionModifiersProvider();
		if (map == null) {
			return original.call(instance);
		}

		return map.entrySet().stream()
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
    @Local(name = "entityPatch") LivingEntityPatch<?> entityPatch
  ) {
    if (imaginarycraft$livingMotionProviderModifiers == null) {
      return original.call(instance, object);
    }

    var functionMap = imaginarycraft$livingMotionProviderModifiers.get((Styles) object);
    if (functionMap == null) {
      return original.call(instance, object);
    }

    return functionMap.entrySet().stream()
	    .map((entry) -> Map.entry(entry.getKey(), entry.getValue().apply(entityPatch)))
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
