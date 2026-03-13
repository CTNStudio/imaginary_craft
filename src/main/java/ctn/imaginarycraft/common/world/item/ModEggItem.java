package ctn.imaginarycraft.common.world.item;

import ctn.imaginarycraft.common.world.entity.abnormalities.AbnormalitiesEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class ModEggItem extends DeferredSpawnEggItem {

  public ModEggItem(Supplier<? extends EntityType<? extends Mob>> entityType, Properties properties) {
    super(entityType, 0, 0, properties);
  }

  @Override
  public @NotNull InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    if (!(level instanceof ServerLevel serverLevel)) {
      return InteractionResult.SUCCESS;
    }

    ItemStack stack = context.getItemInHand();
    BlockPos pos = context.getClickedPos();
    Direction direction = context.getClickedFace();
    BlockState blockState = level.getBlockState(pos);
    BlockEntity blockEntity = level.getBlockEntity(pos);

    // 处理刷怪笼
    if (blockEntity instanceof SpawnerBlockEntity spawner) {
      EntityType<?> entityType = this.getType(stack);
      spawner.setEntityId(entityType, level.getRandom());
      level.sendBlockUpdated(pos, blockState, blockState, 3);
      level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);
      stack.shrink(1);
      return InteractionResult.CONSUME;
    }

    // 确定生成位置
    BlockPos spawnPos;
    if (blockState.getCollisionShape(level, pos).isEmpty()) {
      spawnPos = pos;
    } else {
      spawnPos = pos.relative(direction);
    }

    EntityType<?> entityType = this.getType(stack);
    Entity entity = entityType.spawn(serverLevel, stack, context.getPlayer(), spawnPos,
      MobSpawnType.SPAWN_EGG, true, !Objects.equals(pos, spawnPos) && direction == Direction.UP);
    if (entity != null) {
      if (entity instanceof AbnormalitiesEntity abnormalities) {
        abnormalities.doWhenSpawnByEggs();
      }

      stack.shrink(1);
      level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
      return InteractionResult.CONSUME;
    }

    return InteractionResult.FAIL;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    BlockHitResult hitResult = getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
    if (hitResult.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
      return InteractionResultHolder.pass(stack);
    }

    if (!(level instanceof ServerLevel serverLevel)) {
      return InteractionResultHolder.success(stack);
    }

    BlockPos pos = hitResult.getBlockPos();
    if (!(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) {
      return InteractionResultHolder.pass(stack);
    }

    if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, hitResult.getDirection(), stack)) {
      return InteractionResultHolder.fail(stack);
    }

    EntityType<?> entityType = this.getType(stack);
    Entity entity = entityType.spawn(serverLevel, stack, player, pos, MobSpawnType.SPAWN_EGG, false, false);
    if (entity == null) {
      return InteractionResultHolder.pass(stack);
    }

    if (entity instanceof AbnormalitiesEntity abnormalities) {
      abnormalities.doWhenSpawnByEggs();
    }

    stack.consume(1, player);
    player.awardStat(Stats.ITEM_USED.get(this));
    level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
    return InteractionResultHolder.consume(stack);
  }
}
