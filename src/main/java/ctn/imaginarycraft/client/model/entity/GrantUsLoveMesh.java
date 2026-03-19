package ctn.imaginarycraft.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.VertexBuilder;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

import java.util.List;
import java.util.Map;

public class GrantUsLoveMesh extends SkinnedMesh {
  public final SkinnedMeshPart root;

  public final SkinnedMeshPart rock;
  public final SkinnedMeshPart tablet;
  public final SkinnedMeshPart tentacle_all_l_1;
  public final SkinnedMeshPart tentacle_all_l_2;
  public final SkinnedMeshPart tentacle_all_l_3;
  public final SkinnedMeshPart tentacle_all_r_1;
  public final SkinnedMeshPart tentacle_all_r_2;
  public final SkinnedMeshPart tentacle_all_r_3;

  public GrantUsLoveMesh(@Nullable Map<String, Number[]> arrayMap, @Nullable Map<MeshPartDefinition, List<VertexBuilder>> partBuilders, @Nullable SkinnedMesh parent, RenderProperties properties) {
    super(arrayMap, partBuilders, parent, properties);
    this.root = getOrLogException(this.parts, "root");
    this.rock = getOrLogException(this.parts, "rock");
    this.tablet = getOrLogException(this.parts, "tablet");
    this.tentacle_all_l_1 = getOrLogException(this.parts, "tentacle.all.L.1");
    this.tentacle_all_l_2 = getOrLogException(this.parts, "tentacle.all.L.2");
    this.tentacle_all_l_3 = getOrLogException(this.parts, "tentacle.all.L.3");
    this.tentacle_all_r_1 = getOrLogException(this.parts, "tentacle.all.R.1");
    this.tentacle_all_r_2 = getOrLogException(this.parts, "tentacle.all.R.2");
    this.tentacle_all_r_3 = getOrLogException(this.parts, "tentacle.all.R.3");
  }

  @Override
  public void draw(PoseStack poseStack, MultiBufferSource bufferSources, RenderType renderType, int packedLight, float r, float g, float b, float a, int overlay, @Nullable Armature armature, OpenMatrix4f[] poses) {
    super.draw(poseStack, bufferSources, renderType, packedLight, r, g, b, a, overlay, armature, poses);
  }

  @Override
  public void draw(PoseStack poseStack, MultiBufferSource bufferSources, RenderType renderType, DrawingFunction drawingFunction, int packedLight, float r, float g, float b, float a, int overlay, @Nullable Armature armature, OpenMatrix4f[] poses) {
    super.draw(poseStack, bufferSources, renderType, drawingFunction, packedLight, r, g, b, a, overlay, armature, poses);
  }
}
