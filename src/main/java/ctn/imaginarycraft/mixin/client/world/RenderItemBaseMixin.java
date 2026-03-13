package ctn.imaginarycraft.mixin.client.world;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.data.ConditionalEntryParser;
import ctn.imaginarycraft.api.data.ConditionalProviderFactory;
import ctn.imaginarycraft.mixed.client.IRenderItemBase;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.registry.entries.EpicFightConditions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
    if (!jsonObj.has("trail_cases")) {
      return;
    }
    imaginarycraft$trailInfoProvider = ConditionalProviderFactory.getProvider(trailInfo,
      jsonObj.get("trail_cases").getAsJsonArray().asList().stream()
        .map(JsonObject.class::cast)
        .map(entry -> {
          List<Condition.EntityPatchCondition> conditionList = Lists.newArrayList();
          var conditionsList = entry.getAsJsonArray("conditions");
          for (int i = 0; i < conditionsList.size(); i++) {
            var jsonObject = conditionsList.get(i).getAsJsonObject();
            String predicateId = jsonObject.get(ConditionalEntryParser.PREDICATE).getAsString();

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
          return Pair.of(ConditionalEntryParser.compose(conditionList), TrailInfo.deserialize(entry.getAsJsonObject("value")));
        }).toList());
  }

  @Override
  public TrailInfo imaginarycraft$getTrailInfoProvider(LivingEntityPatch<?> livingEntityPatch) {
    return imaginarycraft$trailInfoProvider != null ? imaginarycraft$trailInfoProvider.apply(livingEntityPatch) : trailInfo();
  }
}
