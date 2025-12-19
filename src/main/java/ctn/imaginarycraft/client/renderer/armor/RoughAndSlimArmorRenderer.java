package ctn.imaginarycraft.client.renderer.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.RenderUtil;

/**
 * 盔甲渲染
 */
public class RoughAndSlimArmorRenderer<T extends Item & GeoItem> extends GeoArmorRenderer<T> {
  protected @Nullable GeoBone slimRightArm;
  protected @Nullable GeoBone slimLeftArm;
  /**
   * 是否是细手臂渲染
   */
  public boolean isSlim;

  public RoughAndSlimArmorRenderer(GeoModel<T> model) {
    super(model);
  }

  @Override
  protected void grabRelevantBones(BakedGeoModel bakedModel) {
    if (this.lastModel == bakedModel) {
      return;
    }

    GeoModel<T> model = getGeoModel();
    this.lastModel = bakedModel;
    this.head = getHeadBone(model);
    this.body = getBodyBone(model);
    this.rightArm = getRightArmBone(model);
    this.leftArm = getLeftArmBone(model);
    this.rightLeg = getRightLegBone(model);
    this.leftLeg = getLeftLegBone(model);
    this.rightBoot = getRightBootBone(model);
    this.leftBoot = getLeftBootBone(model);

    // 细模型部分
    this.slimRightArm = getFineRightArmBone(model);
    this.slimLeftArm = getFineLeftArmBone(model);
  }

  @Nullable
  public GeoBone getFineRightArmBone(@NotNull GeoModel<T> model) {
    return model.getBone("armorFineRightArm").orElse(null);
  }

  @Nullable
  public GeoBone getFineLeftArmBone(@NotNull GeoModel<T> model) {
    return model.getBone("armorFineLeftArm").orElse(null);
  }

  @Override
  public void applyBoneVisibilityByPart(EquipmentSlot currentSlot, ModelPart currentPart, HumanoidModel<?> model) {
    setAllVisible(false);

    currentPart.visible = true;
    GeoBone bone = null;

    if (currentPart == model.hat || currentPart == model.head) {
      bone = this.head;
    } else if (currentPart == model.body) {
      bone = this.body;
    } else if (currentPart == model.leftLeg) {
      bone = currentSlot == EquipmentSlot.FEET ? this.leftBoot : this.leftLeg;
    } else if (currentPart == model.rightLeg) {
      bone = currentSlot == EquipmentSlot.FEET ? this.rightBoot : this.rightLeg;
    } else if (currentPart == model.leftArm && isABoolean(this.slimRightArm)) {
      bone = this.leftArm;
    } else if (currentPart == model.rightArm && isABoolean(this.slimLeftArm)) {
      bone = this.rightArm;
    } else if (currentPart == model.rightArm && this.slimRightArm != null) {
      bone = this.slimRightArm;
    } else if (currentPart == model.leftArm && this.slimLeftArm != null) {
      bone = this.slimLeftArm;
    }

    if (bone != null) {
      bone.setHidden(false);
    }
  }

  @Override
  protected void setAllBonesVisible(boolean visible) {
    super.setAllBonesVisible(visible);

    setBoneVisible(this.slimRightArm, visible);
    setBoneVisible(this.slimLeftArm, visible);
  }

  @Override
  protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
    super.applyBaseTransformations(baseModel);

    if (this.slimRightArm != null) {
      ModelPart rightArmPart = baseModel.rightArm;

      RenderUtil.matchModelPartRot(rightArmPart, this.slimRightArm);
      this.slimRightArm.updatePosition(rightArmPart.x + 5, 2 - rightArmPart.y, rightArmPart.z);
    }

    if (this.slimLeftArm != null) {
      ModelPart leftArmPart = baseModel.leftArm;

      RenderUtil.matchModelPartRot(leftArmPart, this.slimLeftArm);
      this.slimLeftArm.updatePosition(leftArmPart.x - 5f, 2f - leftArmPart.y, leftArmPart.z);
    }
  }

  @Override
  public void prepForRender(Entity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> baseModel, MultiBufferSource bufferSource, float partialTick, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch) {
    super.prepForRender(entity, stack, slot, baseModel, bufferSource, partialTick, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
    if (getCurrentEntity() instanceof AbstractClientPlayer player &&
      (this.slimRightArm != null || this.slimLeftArm != null)) {
      this.isSlim = player.getSkin().model() == PlayerSkin.Model.SLIM;
    }
  }

  @Override
  public void doPostRenderCleanup() {
    super.doPostRenderCleanup();
    this.isSlim = false;
  }

  @Override
  protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
    setAllBonesVisible(false);
    HumanoidModel<?> model = this;

    switch (currentSlot) {
      case HEAD -> setBoneVisible(this.head, model.head.visible);
      case CHEST -> {
        setBoneVisible(this.body, model.body.visible);
        HandApplyBoneVisibilityBySlot(model);
      }
      case LEGS -> {
        setBoneVisible(this.rightLeg, model.rightLeg.visible);
        setBoneVisible(this.leftLeg, model.leftLeg.visible);
      }
      case FEET -> {
        setBoneVisible(this.rightBoot, model.rightLeg.visible);
        setBoneVisible(this.leftBoot, model.leftLeg.visible);
      }
    }
  }

  private void HandApplyBoneVisibilityBySlot(HumanoidModel<?> model) {
    setBoneVisible(this.rightArm, model.rightArm.visible && !this.isSlim);
    setBoneVisible(this.leftArm, model.leftArm.visible && !this.isSlim);

    setBoneVisible(this.slimRightArm, model.rightArm.visible && this.isSlim);
    setBoneVisible(this.slimLeftArm, model.leftArm.visible && this.isSlim);
  }

  private boolean isABoolean(@Nullable GeoBone slimArm) {
    return slimArm == null || !this.isSlim;
  }
}
