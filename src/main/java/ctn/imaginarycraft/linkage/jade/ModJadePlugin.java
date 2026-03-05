package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.block.*;
import snownee.jade.api.*;

@WailaPlugin
public final class ModJadePlugin implements IWailaPlugin {
  public static final ResourceLocation ENTITY_LC_LEVEL = ImaginaryCraft.modRl("entity_lobotomy_corporation_level");
  public static final ResourceLocation BLOCK_LC_LEVEL = ImaginaryCraft.modRl("block_lobotomy_corporation_level");
  public static final ResourceLocation ENTITY_LC_VULNERABLE = ImaginaryCraft.modRl("entity_lobotomy_corporation_vulnerable");

  @Override
  public void register(IWailaCommonRegistration registration) {
  }

  @Override
  public void registerClient(IWailaClientRegistration registration) {
    registration.registerEntityComponent(EntityLcLevel.INSTANCE, Entity.class);
    registration.registerBlockComponent(BlockLcLevel.INSTANCE, Block.class);
    registration.registerEntityComponent(LivingEntityVulnerable.INSTANCE, LivingEntity.class);
  }
}
