package ctn.imaginarycraft.init.world.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class AbnormalitiesSpawnEggItem extends Item {
  private final EntityType<? extends Mob> entityType;

  public AbnormalitiesSpawnEggItem(EntityType<? extends Mob> entityType, Properties properties) {
    super(properties);
    this.entityType = entityType;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    }


    var hitResult = context.getClickLocation();
    var player = context.getPlayer();
    var stack = context.getItemInHand();

    // 在服务端生成实体
    if (level instanceof ServerLevel serverLevel) {
      // 创建实体
      Mob entity = (Mob) entityType.create(serverLevel);
      if (entity != null) {
        entity.setPos(hitResult.x, hitResult.y + 0.5, hitResult.z);

        if (player != null) {
          entity.setYRot(player.getYRot());
        }

        // 添加到世界
        serverLevel.addFreshEntity(entity);

        // 消耗物品（非创造模式）
        if (player != null && !player.isCreative()) {
          stack.shrink(1);
        }

        return InteractionResult.SUCCESS;
      }
    }

    return InteractionResult.FAIL;
  }
}
