package ctn.imaginarycraft.util;

import ctn.imaginarycraft.mixed.client.IKeyMapping;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class PlayerKeyClickUtil {
  public static final String KEY_USE = "key.use";
  public static final String KEY_ATTACK = "key.attack";

  private static final PlayerKeyClickUtil INSTANCE = new PlayerKeyClickUtil();
  private final Map<UUID, @NotNull Map<@NotNull String, @NotNull ClickState>> clickMap = new HashMap<>();

  public static void keyProcess(KeyMapping key, PlayerKeyClickUtil util, LocalPlayer player) {
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

  public enum ClickState {
    NONE,
    PRESS,
    LONG_PRESS,
    RELEASE
  }
}
