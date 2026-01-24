package ctn.imaginarycraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import ctn.imaginarycraft.api.ColourText;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.mixed.client.IKeyMapping;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class PlayerKeyClickUtil {
  public static final String KEY_USE = "key.use";
  public static final String KEY_ATTACK = "key.attack";

  private static final PlayerKeyClickUtil INSTANCE = new PlayerKeyClickUtil();
  private final Map<UUID, @NotNull Map<@NotNull String, @NotNull ClickState>> clickMap = new HashMap<>();

  public static void keyProcess(KeyMapping key, PlayerKeyClickUtil util, Player player) {
    ClickState clickState = util.getClickState(player, key.getName());
    if (key.isDown() && isPress(clickState)) {
      IKeyMapping i = IKeyMapping.of(key);
      int count = i.imaginarycraft$getClickCount();
      util.setClickState(player, key.getName(), count > 3 ? ClickState.LONG_PRESS : ClickState.PRESS);
      return;
    }
    if (isPress(clickState)) {
      util.setClickState(player, key.getName(), ClickState.RELEASE);
    }
  }

  public static boolean isPress(ClickState clickState) {
    return clickState == ClickState.PRESS || clickState == ClickState.LONG_PRESS;
  }

  public static PlayerKeyClickUtil of() {
    return INSTANCE;
  }

  public ClickState getUseState(Player player) {
    return getClickState(player, KEY_USE);
  }

  public ClickState getAttackState(Player player) {
    return getClickState(player, KEY_ATTACK);
  }

  public ClickState getClickState(Player player, String key) {
    return clickMap.getOrDefault(player.getUUID(), Map.of()).getOrDefault(key, ClickState.RELEASE);
  }

  public void setClickState(Player player, String key, ClickState value) {
    clickMap.computeIfAbsent(player.getUUID(), (a) -> {
      Map<String, ClickState> map = new HashMap<>();
      map.put(key, value);
      return map;
    }).put(key, value);
  }

  public enum ClickState implements StringRepresentable {
    NONE(1,"none"),
    PRESS(2,"press"),
    LONG_PRESS(3,"long_press"),
    RELEASE(4,"release");

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
    public String getSerializedName() {
      return name;
    }

    public int getIndex() {
      return index;
    }
  }
}
