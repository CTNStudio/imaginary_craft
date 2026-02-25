package ctn.imaginarycraft.core;

import ctn.imaginarycraft.api.DelayTaskHolder;
import ctn.imaginarycraft.common.item.ego.weapon.melee.RedEyesTachiItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public final class ModEpicjightEventHooks {

  static void listenerRegister() {
    skill();
  }

  private static void skill() {
    EpicFightEventHooks.Player.CAST_SKILL.registerEvent(event -> {
      if (SkillCategories.WEAPON_INNATE != event.getSkillContainer().getSlot().category()) {
        return;
      }
      PlayerPatch<?> playerPatch = event.getPlayerPatch();
      ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
      if (itemStack.getItem() instanceof RedEyesTachiItem redEyesTachiItem) {
        redEyesTachiItem.phase1(itemStack);
        DelayTaskHolder.of(playerPatch.getOriginal()).addTask(InteractionHand.MAIN_HAND, DelayTaskHolder.createTaskBilder()
          .removedRun(() -> redEyesTachiItem.phase(itemStack))
          .resultRun(() -> redEyesTachiItem.phase(itemStack))
          .removedTick(20 * 2)
          .build());
      }
    });
  }

  private ModEpicjightEventHooks() {
  }
}
