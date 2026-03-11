package ctn.imaginarycraft.mixin.client.world;

import com.google.common.collect.*;
import com.google.gson.*;
import com.llamalad7.mixinextras.sugar.*;
import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.mixed.client.*;
import net.minecraft.resources.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import top.theillusivec4.curios.api.type.capability.*;
import yesman.epicfight.api.client.animation.property.*;
import yesman.epicfight.client.renderer.patched.item.*;
import yesman.epicfight.data.conditions.*;
import yesman.epicfight.registry.entries.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

import java.util.*;
import java.util.function.*;

@Mixin(RenderItemBase.class)
public abstract class RenderItemBaseMixin implements IRenderItemBase {
  @Shadow
  @Final
  private TrailInfo trailInfo;

  @Shadow
  public abstract TrailInfo trailInfo();

  @Unique
  private Function<LivingEntityPatch<?>, TrailInfo> imaginarycraft$trailInfoProvider;

  @Inject(method = "<init>", at = @At(value = "INVOKE", ordinal = 1,
    target = "Lcom/google/gson/JsonObject;has(Ljava/lang/String;)Z"))
  private void imaginarycraft$init(
    JsonElement jsonElement,
    CallbackInfo ci,
    @Local(name = "jsonObj") JsonObject jsonObj
  ) {
    if (!jsonObj.has("trail_expand")) {
      return;
    }
    imaginarycraft$trailInfoProvider = ConditionalProviderFactory.getProvider(trailInfo,
      jsonObj.get("trail_expand").getAsJsonArray().asList().stream()
        .map(JsonObject.class::cast)
        .map(entry -> {
          List<Condition.EntityPatchCondition> conditionList = Lists.newArrayList();
          var conditionsList = entry.getAsJsonArray("conditions");
          for (int i = 0; i < conditionsList.size(); i++) {
            var jsonObject = conditionsList.get(i).getAsJsonObject();
            String predicateId = jsonObject.get(ModWeaponTypeReloadListener.PREDICATE_TAG).getAsString();

            try {
              Supplier<Condition.EntityPatchCondition> conditionProvider = EpicFightConditions.getConditionOrThrow(ResourceLocation.parse(predicateId));
              Condition.EntityPatchCondition entityPatchCondition = conditionProvider.get();
              entityPatchCondition.read(jsonObject);
              conditionList.add(entityPatchCondition);
            } catch (Exception e) {
              ICuriosItemHandler.LOGGER.error("Failed to parse condition [index={}, predicate={}, error={}]: {}",
                i, predicateId, e.getClass().getSimpleName(), e.getMessage(), e);
            }
          }
          return Pair.of(ConditionalEntryParser.composePredicate(conditionList), TrailInfo.deserialize(entry.getAsJsonObject("value")));
        }).toList());
  }

  @Override
  public TrailInfo getImaginarycraft$trailInfoProvider(LivingEntityPatch<?> livingEntityPatch) {
    return imaginarycraft$trailInfoProvider != null ? imaginarycraft$trailInfoProvider.apply(livingEntityPatch) : trailInfo();
  }
}
