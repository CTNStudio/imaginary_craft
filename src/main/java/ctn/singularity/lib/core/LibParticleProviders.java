package ctn.singularity.lib.core;

import ctn.singularity.lib.client.particles.TextParticle;
import ctn.singularity.lib.init.LibParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = LibMain.LIB_ID, value = Dist.CLIENT)
public final class LibParticleProviders {
	@SubscribeEvent
	public static void particleProviders(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(LibParticleTypes.TEXT_PARTICLE_TYPE.get(), TextParticle.Provider::new);
	}
}
