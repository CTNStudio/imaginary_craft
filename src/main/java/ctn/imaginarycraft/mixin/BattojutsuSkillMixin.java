package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.skill.weaponinnate.*;

@Mixin(BattojutsuSkill.class)
public abstract class BattojutsuSkillMixin {
  @ModifyExpressionValue(method = "playSkillAnimation", at = @At(value = "INVOKE", target = "Lyesman/epicfight/skill/SkillDataManager;getDataValue(Lnet/neoforged/neoforge/registries/DeferredHolder;)Ljava/lang/Object;"))
  private Object imaginarycraft$playSkillAnimation(Object original) {
    return original == null ? false : original;
  }
}
