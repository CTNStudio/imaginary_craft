package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.client.particle.*;
import ctn.imaginarycraft.client.particle.magicbullet.*;
import ctn.imaginarycraft.client.particle.solemnlament.*;
import ctn.imaginarycraft.client.particle.text.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class ParticleProvidersRegistry {
  @SubscribeEvent
  public static void registry(RegisterParticleProvidersEvent event) {
    event.registerSpecial(ModParticleTypes.TEXT.get(), new TextParticle.Provider());
    event.registerSpecial(ModParticleTypes.DAMAGE_TEXT.get(), new DamageTextParticle.Provider());
    event.registerSpriteSet(ModParticleTypes.LC_DAMAGE_ICON.get(), LcDamageIconParticle.Provider::new);
    event.registerSpriteSet(ModParticleTypes.DYEING_MAGIC_CIRCLE.get(), DyeingMagicCircleParticle.Provider::new);
    event.registerSpriteSet(ModParticleTypes.MAGIC_BULLET_MAGIC_CIRCLE.get(), MagicBulletMagicCircleParticle.Provider::new);
    event.registerSpriteSet(ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_BLACK.get(), ButterflyParticle.Provider::new);
    event.registerSpriteSet(ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_WHITE.get(), ButterflyParticle.Provider::new);
  }
}
