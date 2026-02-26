package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.skill.weaponinnate.BattojutsuSkill;

@Mixin(BattojutsuSkill.class)
public abstract class BattojutsuSkillMixin {
  @ModifyExpressionValue(method = "playSkillAnimation", at = @At(value = "INVOKE", target = "Lyesman/epicfight/skill/SkillDataManager;getDataValue(Lnet/neoforged/neoforge/registries/DeferredHolder;)Ljava/lang/Object;"))
  private Object playSkillAnimation(Object original) {
    return original == null ? false : original;
  }
}
