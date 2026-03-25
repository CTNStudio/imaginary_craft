package ctn.imaginarycraft.client.model.animmodels.animations.grantuslove;

import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class GrantUsLoveTentacleAttackAnimation extends AttackAnimation {

  public static final AnimationProperty.PoseModifier COMBO_ATTACK_DIRECTION_MODIFIER = (self, pose, entitypatch, time, partialTicks) -> {
//    if (!self.isStaticAnimation() || entitypatch instanceof PlayerPatch<?> playerpatch && playerpatch.isFirstPerson()) {
//      return;
//    }
//
//    float pitch = entitypatch.getAttackDirectionPitch(partialTicks);
//    JointTransform chest = pose.orElseEmpty("Chest");
//    chest.frontResult(JointTransform.rotation(QuaternionUtils.XP.rotationDegrees(-pitch)), OpenMatrix4f::mulAsOriginInverse);
//
//    if (entitypatch instanceof PlayerPatch) {
//      float xRot = MathUtils.lerpBetween(entitypatch.getOriginal().xRotO, entitypatch.getOriginal().getXRot(), partialTicks);
//      OpenMatrix4f toOriginalRotation = entitypatch.getArmature().getBoundTransformFor(pose, entitypatch.getArmature().searchJointByName("Head")).removeScale().removeTranslation().invert();
//      Vec3f xAxis = OpenMatrix4f.transform3v(toOriginalRotation, Vec3f.X_AXIS, null);
//      OpenMatrix4f headRotation = OpenMatrix4f.createRotatorDeg(-(pitch + xRot), xAxis);
//
//      pose.orElseEmpty("Head").frontResult(JointTransform.fromMatrix(headRotation), OpenMatrix4f::mul);
//    }
  };

  public GrantUsLoveTentacleAttackAnimation(float transitionTime, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
    super(transitionTime, accessor, armature, phases);
    addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F));
  }

  public GrantUsLoveTentacleAttackAnimation(float transitionTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
    super(transitionTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
    addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F));
  }
}
