package ctn.imaginarycraft.client.registry;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarPlayAnimations {
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("standby");
  public static final ResourceLocation ATTACK = ImaginaryCraft.modRl("attack");

  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(STANDBY, 500,
      player -> new PlayerAnimationController(player,
        (controller, state, animSetter) -> PlayState.STOP
      )
    );
    PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ATTACK, 1500,
      player -> new PlayerAnimationController(player,
        (controller, state, animSetter) -> PlayState.STOP
      )
    );
  }
}
