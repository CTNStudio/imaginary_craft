package ctn.imaginarycraft.core;

import ctn.imaginarycraft.event.rationality.RationalityModifyEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModLoader;

import java.util.Map;

public final class ModEventHooks {
  public static Map.Entry<Boolean, Float> sourceRationalityPre(Player player, float oldValue, float newValue) {
    var event = new RationalityModifyEvent.Pre(player, oldValue, newValue);
    ModLoader.postEvent(event);
    var canceled = event.isCanceled();
    return Map.entry(canceled,
      canceled ? event.getOldValue() : event.getNewValue());
  }

  public static void sourceRationalityPost(Player player, float oldValue, float newValue) {
    ModLoader.postEvent(new RationalityModifyEvent.Post(player, oldValue, newValue));
  }

  /**
   * 处理主动技能释放前事件
   *
   * @param player         玩家
   * @param skillContainer 技能容器
   * @param arguments      技能参数
   * @return 是否允许释放技能
   */
  public static boolean handleSkillCastPre(ServerPlayer player, Object skillContainer, Object arguments) {
    // 这里可以添加全局的技能释放检查逻辑
    // 例如：检查冷却时间、消耗资源、状态限制等

    // 示例：可以根据玩家的四徳属性来影响技能释放
    /*
    var fortitude = player.getData(ModAttachments.FORTITUDE);
    var prudence = player.getData(ModAttachments.PRUDENCE);
    var temperance = player.getData(ModAttachments.TEMPERANCE);
    var justice = player.getData(ModAttachments.JUSTICE);

    // 根据属性值决定是否允许释放技能
    if (fortitude.getValue() < 10) {
      // 勇气不足，阻止技能释放
      return false;
    }
    */

    return true; // 允许技能释放
  }

  /**
   * 处理主动技能释放后事件
   *
   * @param player         玩家
   * @param skillContainer 技能容器
   * @param arguments      技能参数
   */
  public static void handleSkillCastPost(ServerPlayer player, Object skillContainer, Object arguments) {
    // 这里可以添加技能释放后的全局处理逻辑
    // 例如：更新统计数据、触发成就、应用全局效果等

    // 示例：增加技能使用次数统计
    /*
    player.getData(ModAttachments.DELAY_TASK_HOLDER).incrementSkillUsageCount();
    */

    // 示例：根据技能类型给予不同的奖励
    /*
    if (skillContainer != null) {
      String skillType = getSkillType(skillContainer);
      switch (skillType) {
        case "attack":
          // 攻击类技能奖励
          break;
        case "defense":
          // 防御类技能奖励
          break;
        case "utility":
          // 辅助类技能奖励
          break;
      }
    }
    */
  }

  /**
   * 处理技能资源消耗
   *
   * @param player         玩家
   * @param skillContainer 技能容器
   * @param resourceType   资源类型
   * @param amount         消耗数量
   * @return 实际消耗数量
   */
  public static int handleSkillResourceConsume(ServerPlayer player, Object skillContainer, String resourceType, int amount) {
    // 这里可以修改技能资源消耗
    // 例如：根据玩家属性减少消耗、增加消耗等

    // 示例：根据谨慎属性减少技能消耗
    /*
    var prudence = player.getData(ModAttachments.PRUDENCE);
    float reduction = prudence.getValue() * 0.01f; // 每点谨慎减少1%消耗
    return Math.max(1, (int)(amount * (1 - reduction)));
    */

    return amount; // 返回原始消耗数量
  }

  private ModEventHooks() {
  }
}
