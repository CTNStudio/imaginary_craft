package ctn.imaginarycraft.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.ctnapi.client.util.ColorUtil;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttributes;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

// TODO 要重置

/**
 * 物品四色属性能力使用要求提示
 * <p>
 * 当一个最后一个值为-1就是包括大于
 */
public class ItemVirtueUsageReq extends ToTooltip {
  public static final StreamCodec<ByteBuf, ItemVirtueUsageReq> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.map(LinkedHashMap::new, VirtueType.STREAM_CODEC, ByteBufCodecs.INT.apply(ByteBufCodecs.list())),
    ItemVirtueUsageReq::apply,
    ItemVirtueUsageReq::new
  );
  private static final String PREFIX = ImaginaryCraft.ID + ":data_components.item_color_usage_req.";
  public static final String All = PREFIX + "all";
  public static final String REQUIREMENT = PREFIX + "requirement";
  public static final String INTERVAL = PREFIX + "interval";
  public static final String NOT_TO_EXCEED = PREFIX + "not_to_exceed";
  public static final String NOT_LOWER_THAN = PREFIX + "not_lower_than";
  public static final String USE_CONDITION = PREFIX + "use_condition";
  private static final Codec<LinkedHashMap<VirtueType, List<Integer>>> LEVELS_CODEC = Codec
    .unboundedMap(VirtueType.CODEC, Codec.list(Codec.INT))
    .xmap(LinkedHashMap::new, Function.identity());
  private static final Codec<ItemVirtueUsageReq> FULL_CODEC = RecordCodecBuilder
    .create(instance -> instance
      .group(LEVELS_CODEC.fieldOf("require")
        .forGetter(req -> req.requireMap))
      .apply(instance, ItemVirtueUsageReq::new));
  public static final Codec<ItemVirtueUsageReq> CODEC = Codec
    .withAlternative(FULL_CODEC, LEVELS_CODEC, ItemVirtueUsageReq::new);

  private final LinkedHashMap<VirtueType, List<Integer>> requireMap;

  public ItemVirtueUsageReq(LinkedHashMap<VirtueType, List<Integer>> map) {
    this.requireMap = map;
  }

  private static LinkedHashMap<VirtueType, List<Integer>> apply(ItemVirtueUsageReq p_340784_) {
    return p_340784_.requireMap;
  }

  public static ItemVirtueUsageReq empty() {
    LinkedHashMap<VirtueType, List<Integer>> map = new LinkedHashMap<>();
    map.put(VirtueType.FORTITUDE, new ArrayList<>());
    map.put(VirtueType.PRUDENCE, new ArrayList<>());
    map.put(VirtueType.TEMPERANCE, new ArrayList<>());
    map.put(VirtueType.JUSTICE, new ArrayList<>());
    map.put(VirtueType.COMPOSITE_RATING, new ArrayList<>());
    return new ItemVirtueUsageReq(map);
  }

  private static void validateCompositeRatingValue(VirtueType attribute, int value) {
    assert attribute != VirtueType.COMPOSITE_RATING || value != 6 :
      "Composite Rating must be between [-1, 1, 2, 3, 4, 5]. Currently, it is: %d".formatted(value);
  }

  private static Component getParameterComponent(boolean detailed, int value) {
    return detailed ? Component.literal(String.valueOf(value)) : Component.translatable(VirtueRating.getRating(value).getName());
  }

  /// 至少 推荐使用方法
  public static ItemVirtueUsageReq notToExceed(VirtueRating fortitudeRating, VirtueRating prudenceRating, VirtueRating temperanceRating, VirtueRating justiceRating, VirtueRating compositeRating) {
    final ItemVirtueUsageReq empty = ItemVirtueUsageReq.empty();
    if (fortitudeRating != null) {
      empty.setNotToExceedValue(VirtueType.FORTITUDE, fortitudeRating);
    }
    if (prudenceRating != null) {
      empty.setNotToExceedValue(VirtueType.PRUDENCE, prudenceRating);
    }
    if (temperanceRating != null) {
      empty.setNotToExceedValue(VirtueType.TEMPERANCE, temperanceRating);
    }
    if (justiceRating != null) {
      empty.setNotToExceedValue(VirtueType.JUSTICE, justiceRating);
    }
    setNotToExceedValue(compositeRating, VirtueType.COMPOSITE_RATING, empty);
    return empty;
  }

  private static void setNotToExceedValue(final VirtueRating compositeRating, VirtueType virtueType, final ItemVirtueUsageReq empty) {
    if (compositeRating == null) {
      return;
    }
    empty.setNotToExceedValue(virtueType, compositeRating);
  }

  //没什么特殊要求的建议使用这个

  /// 至多
  public static ItemVirtueUsageReq notLowerThan(VirtueRating fortitudeRating, VirtueRating prudenceRating, VirtueRating temperanceRating, VirtueRating justiceRating, VirtueRating compositeRating) {
    final ItemVirtueUsageReq empty = ItemVirtueUsageReq.empty();
    if (fortitudeRating != null) {
      empty.setNotLowerThanValue(VirtueType.FORTITUDE, fortitudeRating);
    }
    if (prudenceRating != null) {
      empty.setNotLowerThanValue(VirtueType.PRUDENCE, prudenceRating);
    }
    if (temperanceRating != null) {
      empty.setNotLowerThanValue(VirtueType.TEMPERANCE, temperanceRating);
    }
    if (justiceRating != null) {
      empty.setNotLowerThanValue(VirtueType.JUSTICE, justiceRating);
    }
    if (compositeRating != null) {
      empty.setNotLowerThanValue(VirtueType.COMPOSITE_RATING, compositeRating);
    }
    return empty;
  }

  public List<Integer> getValue(VirtueType attribute) {
    return getAttributeList(attribute);
  }

  /**
   * 至少
   */
  public ItemVirtueUsageReq setNotToExceedValue(VirtueType attribute, VirtueRating rating) {
    assert attribute != VirtueType.COMPOSITE_RATING || rating != VirtueRating.EX :
      "Composite Rating must be between I and V. Currently, it is: %s".formatted(rating);
    List<Integer> list = getAttributeList(attribute);
    list.clear();
    list.add(rating.getMinValue());
    list.add(-1);
    return this;
  }

  /**
   * 至多
   */
  public ItemVirtueUsageReq setNotLowerThanValue(VirtueType attribute, VirtueRating rating) {
    assert attribute != VirtueType.COMPOSITE_RATING || rating != VirtueRating.EX :
      "Composite Rating must be between I and V. Currently, it is: %s".formatted(rating);
    List<Integer> list = getAttributeList(attribute);
    list.clear();
    list.add(-1);
    list.add(rating.getMinValue());
    return this;
  }

  public ItemVirtueUsageReq setValue(VirtueType attribute, VirtueRating... ratings) {
    if (attribute == VirtueType.COMPOSITE_RATING) {
      for (VirtueRating rating : ratings) {
        assert rating != VirtueRating.EX :
          "Composite Rating must be between I and V. Currently, it is: %s".formatted(rating);
      }
    }
    List<Integer> list = getAttributeList(attribute);
    Arrays.sort(ratings);
    list.clear();
    list.add(ratings[0].getMinValue());
    list.add(ratings[1].getMinValue());
    return this;
  }

  /**
   * 适合仅对应的值可用 -1代表没有限制
   * <p>
   * 注意{@link VirtueType#COMPOSITE_RATING}只有6个值（-1、1、2、3、4、5）
   */
  public ItemVirtueUsageReq setValue(VirtueType attribute, int... values) {
    if (attribute == VirtueType.COMPOSITE_RATING) {
      for (int value : values) {
        assert value != 6 :
          "Composite Rating must be between [-1, 1, 2, 3, 4, 5]. Currently, it is: %d".formatted(value);
      }
    }
    List<Integer> list = getAttributeList(attribute);
    list.clear();
    if (values.length == 0) {
      list.add(-1);
      return this;
    }
    Arrays.sort(values);
    for (int value : values) {
      if (value == -1) {
        list.clear();
        list.add(-1);
        return this;
      }
      list.add(value);
    }
    return this;
  }

  public ItemVirtueUsageReq setMinValue(VirtueType attribute, int value) {
    validateCompositeRatingValue(attribute, value);
    List<Integer> list = getAttributeList(attribute);
    list.clear();
    if (value == 0 || value == -1) {
      list.add(-1);
      return this;
    }
    list.add(value);
    list.add(-1);
    return this;
  }

  public ItemVirtueUsageReq setMaxValue(VirtueType attribute, int value) {
    validateCompositeRatingValue(attribute, value);
    List<Integer> list = getAttributeList(attribute);
    list.clear();
    if (value == 0 || value == -1) {
      list.add(-1);
      return this;
    }
    list.add(-1);
    list.add(value);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof ItemVirtueUsageReq itemVirtueUsageReq) {
      return itemVirtueUsageReq.requireMap.equals(requireMap);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return requireMap.hashCode() * VirtueType.values().length;
  }

  private List<Integer> getAttributeList(VirtueType attribute) {
    if (requireMap.get(attribute) == null) {
      requireMap.put(attribute, new ArrayList<>());
    }
    return requireMap.get(attribute);
  }

  public ItemVirtueUsageReq clearAttributeList(VirtueType attribute) {
    getAttributeList(attribute).clear();
    return this;
  }

  /**
   * 列表是否为空
   */
  public boolean isListEmpty(List<Integer> list) {
    if (list.isEmpty()) {
      return true;
    }
    return list.size() == 1 && list.get(0) == -1;
  }

  /**
   * 获取属性对应的使用要求
   */
  public Component analysisUsageReq(VirtueType attribute, boolean detailed) {
    List<Integer> list = getAttributeList(attribute);
    if (attribute == VirtueType.COMPOSITE_RATING) {
      detailed = false;
    }
    int color = attribute.getColourValue();

    // 根据类型生成
    MutableComponent component = attribute != VirtueType.COMPOSITE_RATING ?
      Component.literal(attribute.getName()).setStyle(Style.EMPTY.withColor(color)) :
      Component.translatable(VirtueType.COMPOSITE_RATING.getSerializedName());

    if (isListEmpty(list)) {
      component.append(Component.translatable(All));
      return component;
    }

    int size = list.size();
    if (size > 2) {
      component.append(Component.translatable(REQUIREMENT));
      for (int i : list) {
        component
          .append(" ")
          .append(getParameterComponent(detailed, i));
      }
      return component;
    }

    int first = list.get(0);
    if (size == 1) {
      if (first == -1) {
        return component;
      }
      component.append(Component.translatable(REQUIREMENT))
        .append(" ")
        .append(getParameterComponent(detailed, first));
      return component;
    }

    if (first == -1) {
      component.append(Component.translatable(NOT_TO_EXCEED))
        .append(" ")
        .append(getParameterComponent(detailed, list.get(1)));
      return component;
    }

    if (list.get(1) == -1) {
      component.append(Component.translatable(NOT_LOWER_THAN))
        .append(" ")
        .append(getParameterComponent(detailed, first));
      return component;
    }

    component.append(Component.translatable(INTERVAL,
      getParameterComponent(detailed, first),
      getParameterComponent(detailed, list.get(1))));
    return component;
  }

  public boolean isAccord(VirtueType attribute, int value) {
    List<Integer> attributeUsageReq = getAttributeList(attribute);
    if (isListEmpty(attributeUsageReq)) {
      return true;
    }
    final int i1 = attributeUsageReq.get(0);
    return switch (attributeUsageReq.size()) {
      case 1 -> i1 == value;
      // 在这 -1 等于 <= 或 >=
      case 2 -> {
        final int i2 = attributeUsageReq.get(1);
        if (i1 == -1 && i2 == -1) {
          yield true;
        }
        if (i1 == -1) {
          yield value <= i2;
        }
        if (i2 == -1) {
          yield value >= i1;
        }
        yield i2 >= value && value >= i1;
      }
      // 在这理论上不应该有-1
      default -> {
        for (int i : attributeUsageReq) {
          if (i != value) {
            continue;
          }
          yield true;
        }
        yield false;
      }
    };
  }

  @Override
  public String toString() {
    return analysisUsageReq(VirtueType.FORTITUDE, true).tryCollapseToString() + "," +
      analysisUsageReq(VirtueType.PRUDENCE, true).tryCollapseToString() + "," +
      analysisUsageReq(VirtueType.TEMPERANCE, true).tryCollapseToString() + "," +
      analysisUsageReq(VirtueType.JUSTICE, true).tryCollapseToString();
  }

  public boolean isEmpty() {
    int i = 0;
    for (VirtueType type : VirtueType.values()) {
      if (isListEmpty(getAttributeList(type))) {
        i++;
      }
    }
    return i == VirtueType.values().length;
  }

  @Override
  public void getToTooltip(List<Component> components) {
    if (isEmpty()) {
      return;
    }
    Minecraft minecraft = Minecraft.getInstance();
    Player player = minecraft.player;

    components.add(Component.literal(USE_CONDITION).setStyle(Style.EMPTY.withColor(ColorUtil.rgbColor("#AAAAAA"))));

    boolean detailed = player != null && (player.getAttributeValue(ModAttributes.INTELLIGENCE_DEPARTMENT_ACTIVATION) == 1 ||
      player.isCreative() && Screen.hasShiftDown());
    for (VirtueType type : VirtueType.values()) {
      if (isListEmpty(getAttributeList(type))) {
        continue;
      }
      components.add(Component.literal(" ").append(analysisUsageReq(type, detailed)));
    }
  }

  public List<Integer> getFortitudeUsageReq() {
    return getAttributeList(VirtueType.FORTITUDE);
  }

  public List<Integer> getPrudenceUsageReq() {
    return getAttributeList(VirtueType.PRUDENCE);
  }

  public List<Integer> getTemperanceUsageReq() {
    return getAttributeList(VirtueType.TEMPERANCE);
  }

  public List<Integer> getJusticeUsageReq() {
    return getAttributeList(VirtueType.JUSTICE);
  }

  public List<Integer> getCompositeRatingUsageReq() {
    return getAttributeList(VirtueType.COMPOSITE_RATING);
  }
}
