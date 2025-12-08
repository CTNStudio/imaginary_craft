package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.client.particle.LcDamageIconParticle;
import ctn.imaginarycraft.client.particle.text.DamageTextParticle;
import ctn.imaginarycraft.client.particle.text.TextParticle;
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
    event.registerSpecial(ModParticleTypes.TEXT.get(), new TextParticle.Provider());
    event.registerSpecial(ModParticleTypes.DAMAGE_TEXT.get(), new DamageTextParticle.Provider());
    event.registerSpriteSet(ModParticleTypes.LC_DAMAGE_ICON.get(), LcDamageIconParticle.Provider::new);
  }
}
