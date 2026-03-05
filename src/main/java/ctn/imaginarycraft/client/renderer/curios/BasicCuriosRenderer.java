package ctn.imaginarycraft.client.renderer.curios;

import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.common.world.item.ego.curio.*;
import ctn.imaginarycraft.datagen.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.renderer.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.*;

public class BasicCuriosRenderer implements ICurioRenderer {
  protected final AnimatableInstanceCache animatableInstanceCache;
  protected final GeoArmorRenderer<EgoCurioItem> renderer;
  protected final EgoCurioItem curioItem;
  protected final RenderType defaultRenderingType;

  public BasicCuriosRenderer(EgoCurioItem curioItem) {
    this(curioItem, RenderType.CUTOUT);
  }

  public BasicCuriosRenderer(EgoCurioItem curioItem, RenderType defaultRenderingType) {
    this.curioItem = curioItem;
    this.animatableInstanceCache = curioItem.getAnimatableInstanceCache();
    this.defaultRenderingType = defaultRenderingType;
    this.renderer = new GeoArmorRenderer<>(curioItem.getModel());
  }

  @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(
    ItemStack stack,
    SlotContext slotContext,
    PoseStack matrixStack,
    RenderLayerParent<T, M> renderLayerParent,
    MultiBufferSource renderTypeBuffer,
    int light,
    float limbSwing,
    float limbSwingAmount,
    float partialTicks,
    float ageInTicks,
    float netHeadYaw,
    float headPitch
  ) {
    var model = renderLayerParent.getModel();
    if (!(model instanceof HumanoidModel<?> humanoidModel)) {
      return;
    }
    EquipmentSlot slot = getEquipmentSlot(slotContext);
    // 预推送当前渲染状态机
    GeoArmorRenderer<EgoCurioItem> renderer = this.renderer;
    renderer.prepForRender(slotContext.entity(), stack, slot, humanoidModel, renderTypeBuffer, partialTicks, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
    // 进行渲染
    renderer.defaultRender(matrixStack, this.curioItem, renderTypeBuffer, null, null, netHeadYaw, partialTicks, light);
  }

  public static @NotNull EquipmentSlot getEquipmentSlot(final SlotContext slotContext) {
    return switch (slotContext.identifier()) {
      case DatagenCuriosTest.EGO_CURIOS_HEADWEAR, DatagenCuriosTest.EGO_CURIOS_HEAD,
           DatagenCuriosTest.EGO_CURIOS_HINDBRAIN, DatagenCuriosTest.EGO_CURIOS_EYE,
           DatagenCuriosTest.EGO_CURIOS_FACE, DatagenCuriosTest.EGO_CURIOS_CHEEK,
           DatagenCuriosTest.EGO_CURIOS_MASK, DatagenCuriosTest.EGO_CURIOS_MOUTH ->
        EquipmentSlot.HEAD;
      case DatagenCuriosTest.EGO_CURIOS_NECK, DatagenCuriosTest.EGO_CURIOS_BROOCH,
           DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK, DatagenCuriosTest.EGO_CURIOS_LEFT_BACK ->
        EquipmentSlot.CHEST;
      case DatagenCuriosTest.EGO_CURIOS_HAND, DatagenCuriosTest.EGO_CURIOS_GLOVE ->
        EquipmentSlot.MAINHAND;
      default -> EquipmentSlot.BODY;
    };
  }
}
