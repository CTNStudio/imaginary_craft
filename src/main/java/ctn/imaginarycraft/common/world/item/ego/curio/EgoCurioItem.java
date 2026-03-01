package ctn.imaginarycraft.common.world.item.ego.curio;

import com.google.common.collect.Multimap;
import ctn.imaginarycraft.api.virtue.VirtueAttributeModifier;
import ctn.imaginarycraft.api.world.item.IEgoItem;
import ctn.imaginarycraft.client.model.GeoCurioModel;
import ctn.imaginarycraft.client.renderer.curios.BasicCuriosRenderer;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.RationalityUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
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

/**
 * E.G.O饰品
 *
 * @author Dusttt
 */
public class EgoCurioItem extends Item implements ICurioItem, GeoItem, IEgoItem {
  // 请不要使用该变量，这些仅用与生成国际化文本
  private @Nullable Map<String, String> tooltipsI18nMap = new LinkedHashMap<>();
  private @Nullable List<Function<String, MutableComponent>> tooltipsComponent;
  private @Nullable List<String> tooltipsI18n;

  private final List<Component> tooltips = new ArrayList<>();
  private final VirtueAttributeModifier virtueAddAttribute;
  private final boolean isEnderMask;
  private final @Nullable GeoCurioModel<EgoCurioItem> model;
  private @Nullable BasicCuriosRenderer curiosRenderer;
  private @Nullable Function<EgoCurioItem, BasicCuriosRenderer> curiosRendererFunction;
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public EgoCurioItem(Builder egoCurioBuilder) {
    super(egoCurioBuilder.properties.component(ModDataComponents.IS_RESTRAIN, false)
      .stacksTo(1)
      .fireResistant());
    this.virtueAddAttribute = egoCurioBuilder.virtueAddAttribute.build();
    this.isEnderMask = egoCurioBuilder.isEnderMask;
    if (FMLEnvironment.dist.isDedicatedServer()) {
      this.model = null;
      this.curiosRenderer = null;
      this.curiosRendererFunction = null;
    } else {
      this.model = egoCurioBuilder.model;
      this.curiosRendererFunction = egoCurioBuilder.curiosRenderer;
    }

    this.tooltipsI18n = egoCurioBuilder.tooltips;
    this.tooltipsComponent = egoCurioBuilder.tooltipsComponent;
  }

  @Override
  public void onUnequip(final SlotContext slotContext, final ItemStack newStackInSlot, final ItemStack stackBeingUnequipped) {
    ICurioItem.super.onUnequip(slotContext, newStackInSlot, stackBeingUnequipped);
    if (slotContext.entity() instanceof Player player) {
      RationalityUtil.restrictValue(player, true);
    }
  }

  @Override
  public void onEquip(final SlotContext slotContext, final ItemStack previousStack, final ItemStack stackBeingEquipped) {
    ICurioItem.super.onEquip(slotContext, previousStack, stackBeingEquipped);
    if (slotContext.entity() instanceof Player player) {
      RationalityUtil.restrictValue(player, true);
    }
  }

  @Override
  protected @NotNull String getOrCreateDescriptionId() {
    String itemDescriptionId = super.getOrCreateDescriptionId();
    if (this.tooltipsI18n == null) {
      return itemDescriptionId;
    }
    AtomicInteger tooltipComponentIndex = new AtomicInteger();
    this.tooltipsI18n.forEach(tooltipValue -> {
      int index = tooltipComponentIndex.get();
      tooltipComponentIndex.incrementAndGet();
      String key = itemDescriptionId + ".tooltip." + index;
      if (!FMLEnvironment.production && this.tooltipsI18nMap != null) {
        this.tooltipsI18nMap.put(key, tooltipValue);
      }
      if (this.tooltipsComponent != null) {
        this.tooltips.add(this.tooltipsComponent.get(index).apply(key));
      }
    });
    if (FMLEnvironment.production && this.tooltipsI18nMap != null) {
      this.tooltipsI18nMap = null;
    }
    this.tooltipsComponent = null;
    this.tooltipsI18n = null;
    return itemDescriptionId;
  }

  @Override
  public List<Component> getSlotsTooltip(final List<Component> originalTooltips, final TooltipContext tooltipContext, final ItemStack itemStack) {
    List<Component> mutableTooltip = new ArrayList<>(originalTooltips);
    mutableTooltip.addAll(this.tooltips);
    return ICurioItem.super.getSlotsTooltip(mutableTooltip, tooltipContext, itemStack);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack itemStack) {
    return true;
  }

  @Override
  public boolean isEnderMask(final SlotContext slotContext, final EnderMan endermanEntity, final ItemStack itemStack) {
    return isEnderMask;
  }

  /**
   * 属性加成
   */
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation attributeId, ItemStack itemStack) {
    Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = ICurioItem.super.getAttributeModifiers(slotContext, attributeId, itemStack);
    attributeModifiers.putAll(this.virtueAddAttribute.getAttributeModifiers(slotContext.entity(), attributeId, itemStack));
    return attributeModifiers;
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
    private final VirtueAttributeModifier.Builder virtueAddAttribute = new VirtueAttributeModifier.Builder();
    private boolean isEnderMask;
    private Properties properties = new Properties();
    private final List<String> tooltips = new ArrayList<>();
    private final List<Function<String, MutableComponent>> tooltipsComponent = new ArrayList<>();
    private @Nullable GeoCurioModel<EgoCurioItem> model;
    private @Nullable Function<EgoCurioItem, BasicCuriosRenderer> curiosRenderer;

    public Builder() {
      this.model = null;
    }

    /**
     * 勇气
     *
     * @param maxHealth 最大生命值
     */
    public Builder fortitude(int maxHealth) {
      this.virtueAddAttribute.fortitude(maxHealth);
      return this;
    }

    /**
     * 谨慎
     *
     * @param maxRationality 最大理智
     */
    public Builder prudence(int maxRationality) {
      this.virtueAddAttribute.prudence(maxRationality);
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
      this.virtueAddAttribute.temperance(blockBreakSpeed, attackKnockback, workSuccessRate, workSpeed);
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
      this.virtueAddAttribute.temperance(blockBreakSpeed, attackKnockback, workValue);
      return this;
    }

    /**
     * 自律
     */
    public Builder temperance(int value) {
      this.virtueAddAttribute.temperance(value);
      return this;
    }

    /**
     * 正义
     *
     * @param movementSpeed 移动速度
     * @param swimSpeed     游泳速度
     * @param attackSpeed   攻击速度
     */
    public Builder justice(int movementSpeed, int swimSpeed, int attackSpeed) {
      this.virtueAddAttribute.justice(movementSpeed, swimSpeed, attackSpeed);
      return this;
    }

    /**
     * 正义
     *
     * @param speed       移动速度，游泳速度
     * @param attackSpeed 攻击速度
     */
    public Builder justice(int speed, int attackSpeed) {
      this.virtueAddAttribute.justice(speed, attackSpeed);
      return this;
    }

    /**
     * 正义
     */
    public Builder justice(int value) {
      this.virtueAddAttribute.justice(value);
      return this;
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
  }
}
