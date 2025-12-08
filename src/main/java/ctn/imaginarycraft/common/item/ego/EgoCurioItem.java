package ctn.imaginarycraft.common.item.ego;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueJustice;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueTemperance;
import ctn.imaginarycraft.client.model.GeoCurioModel;
import ctn.imaginarycraft.client.renderer.curios.BasicCuriosRenderer;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static ctn.imaginarycraft.common.item.ego.EgoCurioItem.Builder.AddAttribute.addAttribute;

/**
 * E.G.O饰品
 *
 * @author Dusttt
 */
public class EgoCurioItem extends EgoItem implements ICurioItem, GeoItem {
  // 请不要使用该变量，这些仅用与生成国际化文本
  private @Nullable Map<String, String> tooltipsI18nMap = new LinkedHashMap<>();
  private @Nullable List<Function<String, MutableComponent>> tooltipsComponent;
  private @Nullable List<String> tooltipsI18n;

  private final List<Component> tooltips = new ArrayList<>();
  private final @Nullable Builder.AddAttribute fortitude;
  private final @Nullable Builder.AddAttribute prudence;
  private final @Nullable Builder.AddAttribute temperance;
  private final @Nullable Builder.AddAttribute justice;
  private final boolean isEnderMask;
  private final @Nullable GeoCurioModel<EgoCurioItem> model;
  private @Nullable BasicCuriosRenderer curiosRenderer;
  private @Nullable Function<EgoCurioItem, BasicCuriosRenderer> curiosRendererFunction;
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public EgoCurioItem(Builder builder) {
    super(builder.properties.component(ModDataComponents.IS_RESTRAIN, false)
      .stacksTo(1)
      .fireResistant());
    this.fortitude = builder.fortitude;
    this.prudence = builder.prudence;
    this.temperance = builder.temperance;
    this.justice = builder.justice;
    this.isEnderMask = builder.isEnderMask;
    if (FMLEnvironment.dist.isDedicatedServer()) {
      this.model = null;
      this.curiosRenderer = null;
      this.curiosRendererFunction = null;
    } else {
      this.model = builder.model;
      this.curiosRendererFunction = builder.curiosRenderer;
    }

    this.tooltipsI18n = builder.tooltips;
    this.tooltipsComponent = builder.tooltipsComponent;
  }

  @Override
  public void onUnequip(final SlotContext slotContext, final ItemStack newStack, final ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);
    if (!(slotContext.entity() instanceof Player player)) {
      return;
    }
    RationalityUtil.restrictValue(player, true);
  }

  @Override
  public void onEquip(final SlotContext slotContext, final ItemStack prevStack, final ItemStack stack) {
    ICurioItem.super.onEquip(slotContext, prevStack, stack);
    if (!(slotContext.entity() instanceof Player player)) {
      return;
    }
    RationalityUtil.restrictValue(player, true);
  }

  @Override
  protected @NotNull String getOrCreateDescriptionId() {
    String orCreateDescriptionId = super.getOrCreateDescriptionId();
    if (this.tooltipsI18n == null) {
      return orCreateDescriptionId;
    }
    AtomicInteger componentKeyIndex = new AtomicInteger();
    this.tooltipsI18n.forEach(value -> {
      int index = componentKeyIndex.get();
      componentKeyIndex.incrementAndGet();
      String key = orCreateDescriptionId + ".tooltip." + index;
      if (!FMLEnvironment.production && this.tooltipsI18nMap != null) {
        this.tooltipsI18nMap.put(key, value);
      }
      if (this.tooltipsComponent != null) {
        this.tooltips.add(this.tooltipsComponent.get(index).apply(key));
      }
    });
    // TODO 目前仅在玩家的分发时才移除
    if (FMLEnvironment.production && this.tooltipsI18nMap != null) {
      this.tooltipsI18nMap = null;
    }
    this.tooltipsComponent = null;
    this.tooltipsI18n = null;
    return orCreateDescriptionId;
  }

  @Override
  public List<Component> getSlotsTooltip(final List<Component> tooltips, final TooltipContext context, final ItemStack stack) {
    List<Component> mutableTooltip = new ArrayList<>(tooltips);
    mutableTooltip.addAll(this.tooltips);
    return ICurioItem.super.getSlotsTooltip(mutableTooltip, context, stack);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  @Override
  public boolean isEnderMask(final SlotContext slotContext, final EnderMan enderMan, final ItemStack stack) {
    return isEnderMask;
  }

  /**
   * 属性加成
   */
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> modifier = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
    apply(slotContext, id, stack, modifier, this.fortitude);
    apply(slotContext, id, stack, modifier, this.prudence);
    apply(slotContext, id, stack, modifier, this.temperance);
    apply(slotContext, id, stack, modifier, this.justice);
    return modifier;
  }

  private static void apply(final SlotContext slotContext, final ResourceLocation id, final ItemStack stack, final Multimap<Holder<Attribute>, AttributeModifier> modifier, final @Nullable Builder.AddAttribute fortitude) {
    if (fortitude == null) {
      return;
    }
    modifier.putAll(fortitude.apply(slotContext.entity(), id, stack));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  @Nullable
  public GeoCurioModel<EgoCurioItem> getModel() {
    return model;
  }

  public Map<String, String> getAndClearTooltipsI18nMap() {
    Map<String, String> map = this.tooltipsI18nMap;
    if (map == null) {
      return null;
    }
    this.tooltipsI18nMap = null;
    return map;
  }

  public @Nullable BasicCuriosRenderer getCuriosRenderer() {
    if (curiosRenderer != null) {
      return curiosRenderer;
    }
    if (curiosRendererFunction == null) {
      return null;
    }
    this.curiosRenderer = curiosRendererFunction.apply(this);
    this.curiosRendererFunction = null;
    return curiosRenderer;
  }

  public static class Builder {
    private @Nullable AddAttribute fortitude;
    private @Nullable AddAttribute prudence;
    private @Nullable AddAttribute temperance;
    private @Nullable AddAttribute justice;
    private boolean isEnderMask;
    private Properties properties = new Properties();
    private final List<String> tooltips = new ArrayList<>();
    private final List<Function<String, MutableComponent>> tooltipsComponent = new ArrayList<>();
    private @Nullable GeoCurioModel<EgoCurioItem> model;
    private @Nullable Function<EgoCurioItem, BasicCuriosRenderer> curiosRenderer;

    public Builder() {
      this.model = null;
    }

    public Builder model(GeoCurioModel<EgoCurioItem> model) {
      if (FMLEnvironment.dist.isDedicatedServer()) {
        return this;
      }
      this.model = model;
      this.curiosRenderer = BasicCuriosRenderer::new;
      return this;
    }

    public Builder model(String modelRl) {
      return model(new GeoCurioModel<>(modelRl));
    }

    public Builder renderer(Function<EgoCurioItem, BasicCuriosRenderer> curiosRenderer) {
      if (FMLEnvironment.dist.isDedicatedServer()) {
        return this;
      }
      this.curiosRenderer = curiosRenderer;
      return this;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }

    /**
     * 勇气
     *
     * @param maxHealth 最大生命值
     */
    public Builder fortitude(int maxHealth) {
      if (maxHealth == 0) {
        return this;
      }
      this.fortitude = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(maxHealth, modifier, id, Attributes.MAX_HEALTH, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 谨慎
     *
     * @param maxRationality 最大理智
     */
    public Builder prudence(int maxRationality) {
      if (maxRationality == 0) {
        return this;
      }
      this.prudence = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(maxRationality, modifier, id, ModAttributes.MAX_RATIONALITY, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }


    /**
     * 自律
     *
     * @param blockBreakSpeed 挖掘速度
     * @param attackKnockback 攻击击退
     * @param workSuccessRate 工作成功率
     * @param workSpeed       工作速度
     */
    public Builder temperance(int blockBreakSpeed, int attackKnockback, int workSuccessRate, int workSpeed) {
      if (blockBreakSpeed == 0 && attackKnockback == 0) {
        return this;
      }
      this.temperance = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        // TODO 补充 成功率，工作速度
        addAttribute(blockBreakSpeed * VirtueTemperance.BLOCK_BREAK_SPEED, modifier, id, Attributes.BLOCK_BREAK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(attackKnockback * VirtueTemperance.ATTACK_KNOCKBACK_SPEED, modifier, id, Attributes.ATTACK_KNOCKBACK, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 自律
     *
     * @param blockBreakSpeed 挖掘速度
     * @param attackKnockback 攻击击退
     * @param workValue       工作成功率，工作速度
     */
    public Builder temperance(int blockBreakSpeed, int attackKnockback, int workValue) {
      return temperance(blockBreakSpeed, attackKnockback, workValue, workValue);
    }

    /**
     * 自律
     */
    public Builder temperance(int value) {
      return temperance(value, value, value, value);
    }

    /**
     * 正义
     *
     * @param movementSpeed 移动速度
     * @param swimSpeed     游泳速度
     * @param attackSpeed   攻击速度
     */
    public Builder justice(int movementSpeed, int swimSpeed, int attackSpeed) {
      if (movementSpeed == 0 && attackSpeed == 0 && swimSpeed == 0) {
        return this;
      }
      this.justice = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(movementSpeed * VirtueJustice.MOVEMENT_SPEED, modifier, id, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(attackSpeed * VirtueJustice.ATTACK_SPEED, modifier, id, Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(swimSpeed * VirtueJustice.SWIM_SPEED, modifier, id, NeoForgeMod.SWIM_SPEED, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 正义
     *
     * @param speed       移动速度，游泳速度
     * @param attackSpeed 攻击速度
     */
    public Builder justice(int speed, int attackSpeed) {
      return justice(speed, speed, attackSpeed);
    }

    /**
     * 正义
     */
    public Builder justice(int value) {
      return justice(value, value, value);
    }

    public Builder enderMask() {
      this.isEnderMask = true;
      return this;
    }

    public Builder addTooltip(String zhCn) {
      this.tooltips.add(zhCn);
      this.tooltipsComponent.add(Component::translatable);
      return this;
    }

    public Builder addTooltip(String zhCn, UnaryOperator<MutableComponent> component) {
      this.tooltips.add(zhCn);
      this.tooltipsComponent.add((key) -> component.apply(Component.translatable(key)));
      return this;
    }

    public Builder addTooltip(String zhCn, Style style) {
      this.tooltips.add(zhCn);
      this.tooltipsComponent.add((key) -> Component.translatable(key).setStyle(style));
      return this;
    }

    @FunctionalInterface
    public interface AddAttribute {
      Multimap<Holder<Attribute>, AttributeModifier> apply(LivingEntity entity, ResourceLocation id, ItemStack stack);

      static void addAttribute(double value, Multimap<Holder<Attribute>, AttributeModifier> multimap, ResourceLocation id, Holder<Attribute> holder, AttributeModifier.Operation operation) {
        if (value == 0) {
          return;
        }
        multimap.put(holder, new AttributeModifier(id, value, operation));
      }
    }
  }
}
