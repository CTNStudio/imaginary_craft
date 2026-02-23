package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.IPlayerItemAttackClick;
import ctn.imaginarycraft.common.payload.tos.PlayerKeyClickPayload;
import ctn.imaginarycraft.mixed.IPlayer;
import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayer {
  @Unique
  private final @NotNull Map<@NotNull String, PlayerKeyClickUtil.@NotNull ClickState> imaginarycraft$clickMap = new HashMap<>();

  // TODO 待实装 攻击段数计数
  @Unique
  private int imaginarycraft$attackSegmentCount = 0;
  @Unique
  private int imaginarycraft$resetTimeForAttackSegmentCount = 0;

  protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public PlayerKeyClickUtil.ClickState imaginarycraft$getClickState(String keyName) {
    return imaginarycraft$clickMap.getOrDefault(keyName, PlayerKeyClickUtil.ClickState.RELEASE);
  }

  @Override
  public void imaginarycraft$setClickState(String keyName, PlayerKeyClickUtil.ClickState clickState, boolean isSync) {
    imaginarycraft$clickMap.put(keyName, clickState);

    Player player = imaginarycraft$getThis();
    if (!isSync || !(player instanceof AbstractClientPlayer)) {
      return;
    }
    if (player.getMainHandItem().getItem() instanceof IPlayerItemAttackClick click) {
      PlayerKeyClickUtil.itemClick(player, clickState, click, true);
    } else if (player.getOffhandItem().getItem() instanceof IPlayerItemAttackClick click) {
      PlayerKeyClickUtil.itemClick(player, clickState, click, false);
    }
    PlayerKeyClickPayload.send(keyName, clickState);
  }

  @Unique
  private @NotNull Player imaginarycraft$getThis() {
    return (Player) (Object) (this);
  }

  @Override
  public int imaginarycraft$getAttackSegmentCount() {
    return imaginarycraft$attackSegmentCount;
  }

  @Override
  public void imaginarycraft$setAttackSegmentCount(int attackSegmentCount) {
    this.imaginarycraft$attackSegmentCount = attackSegmentCount;
  }

  @Override
  public int getImaginarycraft$resetTimeForAttackSegmentCount() {
    return imaginarycraft$resetTimeForAttackSegmentCount;
  }

  @Override
  public void setImaginarycraft$resetTimeForAttackSegmentCount(int imaginarycraft$resetTimeForAttackSegmentCount) {
    this.imaginarycraft$resetTimeForAttackSegmentCount = imaginarycraft$resetTimeForAttackSegmentCount;
  }
}
