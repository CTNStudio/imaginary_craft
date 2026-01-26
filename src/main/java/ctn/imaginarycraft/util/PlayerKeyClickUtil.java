package ctn.imaginarycraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import ctn.imaginarycraft.api.IPlayerItemAttackClick;
import ctn.imaginarycraft.mixed.IPlayer;
import ctn.imaginarycraft.mixed.client.IKeyMapping;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerKeyClickUtil {
  public static final String KEY_ATTACK = "key.attack";
  public static final String KEY_USE = "key.use";

  private PlayerKeyClickUtil() {
  }

  public static void clientTickProcess(Options options, Minecraft minecraft, Player player) {
    if (minecraft.screen != null) {
      handleAllKeysRelease(player, options);
      return;
    }
    KeyMapping keyAttack = options.keyAttack;
    KeyMapping keyUse = options.keyUse;
    keyProcess(player, keyAttack);
    keyProcess(player, keyUse);
  }

  public static void itemClick(Player player, ClickState clickState, IPlayerItemAttackClick iPlayerItemAttackClick, boolean isMainHand) {
    InteractionHand hand = isMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    switch (clickState) {
      case PRESS -> iPlayerItemAttackClick.onAttackClick(player, hand);
      case RELEASE -> iPlayerItemAttackClick.onAttackClickRelease(player, hand);
    }
  }

  public static ClickState getAttackState(Player player) {
    return getClickState(player, KEY_ATTACK);
  }

  public static ClickState getClickState(Player player, String keyName) {
    return IPlayer.of(player).imaginarycraft$getClickState(keyName);
  }

  public static void keyProcess(Player player, KeyMapping key) {
    ClickState clickState = getClickState(player, key.getName());
    if (keyPress(player, key, clickState)) return;
    handleReleaseState(player, key, clickState);
  }

  public static boolean keyPress(Player player, KeyMapping key, ClickState clickState) {
    if (!key.isDown() || isPress(clickState)) {
      return false;
    }
    setClickState(player, key.getName(), ClickState.PRESS);
    return true;
  }

  public static void handleReleaseState(Player player, KeyMapping key, ClickState clickState) {
    if (isPress(clickState)) {
      setClickState(player, key.getName(), ClickState.RELEASE);
    }
  }

  public static void handleAllKeysRelease(Player player) {
    if (player instanceof AbstractClientPlayer) {
      handleAllKeysRelease(player, Minecraft.getInstance().options, false);
      return;
    }
    setClickState(player, KEY_USE, ClickState.RELEASE, false);
    setClickState(player, KEY_ATTACK, ClickState.RELEASE, false);
  }

  public static void handleAllKeysRelease(Player player, Options options, boolean isSync) {
    KeyMapping keyAttack = options.keyAttack;
    KeyMapping keyUse = options.keyUse;
    handleKeyRelease(player, keyAttack, isSync);
    handleKeyRelease(player, keyUse, isSync);
  }

  public static void handleAllKeysRelease(Player player, Options options) {
    handleAllKeysRelease(player, options, true);
  }

  public static void handleKeyRelease(PlayerKeyClickUtil playerKeyClickUtil, Player player, KeyMapping key) {
    handleKeyRelease(player, key, true);
  }

  public static void handleKeyRelease(Player player, KeyMapping key, boolean isSync) {
    if (isPress(getClickState(player, key))) {
      setClickState(player, key, ClickState.RELEASE, isSync);
    }
  }

  public static boolean isPress(ClickState clickState) {
    return clickState == ClickState.PRESS;
  }

  public static ClickState getClickState(Player player, KeyMapping keyMapping) {
    return getClickState(player, keyMapping.getName());
  }

  public static void setClickState(Player player, KeyMapping keyMapping, ClickState clickState, boolean isSync) {
    setClickState(player, keyMapping.getName(), clickState, isSync);
  }

  public static void setClickState(Player player, String keyName, ClickState clickState, boolean isSync) {
    IPlayer.of(player).imaginarycraft$setClickState(keyName, clickState, isSync);
  }

  public static void setClickState(Player player, KeyMapping keyMapping, ClickState clickState) {
    setClickState(player, keyMapping, clickState, true);
  }

  public static void setClickState(Player player, String keyName, ClickState clickState) {
    setClickState(player, keyName, clickState, true);
  }

  public static ClickState getUseState(Player player) {
    return getClickState(player, KEY_USE);
  }

  public enum ClickState implements StringRepresentable {
    PRESS(0, "press"),
    RELEASE(1, "release");

    public static final Codec<ClickState> CODEC = StringRepresentable
      .fromEnum(ClickState::values).validate(DataResult::success);
    public static final StreamCodec<ByteBuf, ClickState> STREAM_CODEC = ByteBufCodecs
      .idMapper(ByIdMap.continuous(ClickState::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP), ClickState::getIndex);

    private final int index;
    private final String name;

    ClickState(int index, String name) {
      this.index = index;
      this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
      return name;
    }

    public int getIndex() {
      return index;
    }
  }
}
