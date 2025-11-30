package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.client.particle.TextParticleProvider;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistryParticleProviders {
  @SubscribeEvent
  public static void registry(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(ModParticleTypes.TEXT_PARTICLE_TYPE.get(), TextParticleProvider::new);
  }
}
