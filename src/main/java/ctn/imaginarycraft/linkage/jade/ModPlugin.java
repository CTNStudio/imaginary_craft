package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class ModPlugin implements IWailaPlugin {
  public static final ResourceLocation ENTITY_LC_LEVEL = ImaginaryCraft.modRl("level");
  public static final ResourceLocation ENTITY_LC_VULNERABLE = ImaginaryCraft.modRl("vulnerable");

  @Override
  public void register(IWailaCommonRegistration registration) {
  }

  @Override
  public void registerClient(IWailaClientRegistration registration) {
    registration.registerEntityComponent(EntityLcLevel.INSTANCE, Entity.class);
    registration.registerEntityComponent(LivingEntityVulnerable.INSTANCE, LivingEntity.class);
  }
}
