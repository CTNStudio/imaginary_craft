package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.api.client.renderer.entity.EmptyLivingEntityRenderer;
import ctn.imaginarycraft.api.client.renderer.entity.EmptyMobRenderer;
import ctn.imaginarycraft.client.model.entity.ModGeoEntityModel;
import ctn.imaginarycraft.client.renderer.entity.MagicBulletRenderer;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.FruitOfUnderstanding;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import ctn.imaginarycraft.init.world.entity.ProjectileEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class EntityRenderersRegistry {
  @SubscribeEvent
  public static void registry(EntityRenderersEvent.RegisterRenderers event) {
    registerEmptyLivingEntityRenderer(event, OrdealsEntityTypes.GRANT_US_LOVE.get(), 2f, ModGeoEntityModel.getTexturePath("grant_us_love"));
    event.registerEntityRenderer(OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING.get(), context ->
      new GeoEntityRenderer<>(context, FruitOfUnderstanding.getModel()));
	  registerEmptyMobRenderer(event, OrdealsEntityTypes.GRANT_US_LOVE.get(), 2f, ModGeoEntityModel.getTexturePath("grant_us_love"));
    event.registerEntityRenderer(ProjectileEntityTypes.MAGIC_BULLET_ENTITY.get(), MagicBulletRenderer::new);
  }

	private static void registerEmptyMobRenderer(EntityRenderersEvent.RegisterRenderers event, EntityType<? extends Mob> entityType, float shadowRadius, ResourceLocation texture) {
		event.registerEntityRenderer(entityType, (context) -> new EmptyMobRenderer<>(context, shadowRadius, texture));
	}

	private static void registerEmptyLivingEntityRenderer(EntityRenderersEvent.RegisterRenderers event, EntityType<? extends LivingEntity> entityType, float shadowRadius, ResourceLocation texture) {
		event.registerEntityRenderer(entityType, (context) -> new EmptyLivingEntityRenderer<>(context, shadowRadius, texture));
	}
}
