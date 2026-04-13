package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.jointpart.JointPartLivingEntityPatch;
import ctn.imaginarycraft.init.epicfight.ModFactions;
import ctn.imaginarycraft.init.epicfight.animmodels.ModAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.damagesource.StunType;

public class GrantUsLoveTentaclePatch extends JointPartLivingEntityPatch<GrantUsLoveTentacle, GrantUsLovePatch> {
	public GrantUsLoveTentaclePatch(GrantUsLoveTentacle entity) {
		super(entity);
	}

	@Override
	public Faction getFaction() {
		return ModFactions.ORDEALS_VIOLET;
	}

	@Override
	public void initAnimator(Animator animator) {
		super.initAnimator(animator);
		// 待机
		animator.addLivingAnimation(LivingMotions.IDLE, ModAnimations.GRANT_US_LOVE_IDLE);
		// 死亡
		animator.addLivingAnimation(LivingMotions.DEATH, ModAnimations.GRANT_US_LOVE_DEATH);
	}

	@Override
	public void updateMotion(boolean considerInaction) {

	}

	@Override
	public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
		return null;
	}
}
