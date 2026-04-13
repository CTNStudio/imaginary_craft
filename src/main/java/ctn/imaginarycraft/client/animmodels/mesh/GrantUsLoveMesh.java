package ctn.imaginarycraft.client.animmodels.mesh;

import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.VertexBuilder;

import java.util.List;
import java.util.Map;

public class GrantUsLoveMesh extends SkinnedMesh {
	public final SkinnedMeshPart root;

	public final SkinnedMeshPart tablet;
	public final SkinnedMeshPart rock;

	public final SkinnedMeshPart tentacle1_0_L;
	public final SkinnedMeshPart tentacle1_1_L;
	public final SkinnedMeshPart tentacle1_2_L;
	public final SkinnedMeshPart tentacle1_3_L;

	public final SkinnedMeshPart tentacle2_0_L;
	public final SkinnedMeshPart tentacle2_1_L;
	public final SkinnedMeshPart tentacle2_2_L;
	public final SkinnedMeshPart tentacle2_3_L;

	public final SkinnedMeshPart tentacle3_0_L;
	public final SkinnedMeshPart tentacle3_1_L;
	public final SkinnedMeshPart tentacle3_2_L;
	public final SkinnedMeshPart tentacle3_3_L;

	public final SkinnedMeshPart tentacle1_0_R;
	public final SkinnedMeshPart tentacle1_1_R;
	public final SkinnedMeshPart tentacle1_2_R;
	public final SkinnedMeshPart tentacle1_3_R;

	public final SkinnedMeshPart tentacle2_0_R;
	public final SkinnedMeshPart tentacle2_1_R;
	public final SkinnedMeshPart tentacle2_2_R;
	public final SkinnedMeshPart tentacle2_3_R;

	public final SkinnedMeshPart tentacle3_0_R;
	public final SkinnedMeshPart tentacle3_1_R;
	public final SkinnedMeshPart tentacle3_2_R;
	public final SkinnedMeshPart tentacle3_3_R;

	public GrantUsLoveMesh(Map<String, Number[]> arrayMap, Map<MeshPartDefinition, List<VertexBuilder>> parts, SkinnedMesh parent, RenderProperties properties) {
		super(arrayMap, parts, parent, properties);
		this.root = getOrLogException(this.parts, "Root");
		this.tablet = getOrLogException(this.parts, "Tablet");

		this.tentacle1_0_L = getOrLogException(this.parts, "Tentacle1_0.L");
		this.tentacle1_1_L = getOrLogException(this.parts, "Tentacle1_1.L");
		this.tentacle1_2_L = getOrLogException(this.parts, "Tentacle1_2.L");
		this.tentacle1_3_L = getOrLogException(this.parts, "Tentacle1_3.L");

		this.tentacle2_0_L = getOrLogException(this.parts, "Tentacle2_0.L");
		this.tentacle2_1_L = getOrLogException(this.parts, "Tentacle2_1.L");
		this.tentacle2_2_L = getOrLogException(this.parts, "Tentacle2_2.L");
		this.tentacle2_3_L = getOrLogException(this.parts, "Tentacle2_3.L");

		this.tentacle3_0_L = getOrLogException(this.parts, "Tentacle3_0.L");
		this.tentacle3_1_L = getOrLogException(this.parts, "Tentacle3_1.L");
		this.tentacle3_2_L = getOrLogException(this.parts, "Tentacle3_2.L");
		this.tentacle3_3_L = getOrLogException(this.parts, "Tentacle3_3.L");

		this.tentacle1_0_R = getOrLogException(this.parts, "Tentacle1_0.R");
		this.tentacle1_1_R = getOrLogException(this.parts, "Tentacle1_1.R");
		this.tentacle1_2_R = getOrLogException(this.parts, "Tentacle1_2.R");
		this.tentacle1_3_R = getOrLogException(this.parts, "Tentacle1_3.R");

		this.tentacle2_0_R = getOrLogException(this.parts, "Tentacle2_0.R");
		this.tentacle2_1_R = getOrLogException(this.parts, "Tentacle2_1.R");
		this.tentacle2_2_R = getOrLogException(this.parts, "Tentacle2_2.R");
		this.tentacle2_3_R = getOrLogException(this.parts, "Tentacle2_3.R");

		this.tentacle3_0_R = getOrLogException(this.parts, "Tentacle3_0.R");
		this.tentacle3_1_R = getOrLogException(this.parts, "Tentacle3_1.R");
		this.tentacle3_2_R = getOrLogException(this.parts, "Tentacle3_2.R");
		this.tentacle3_3_R = getOrLogException(this.parts, "Tentacle3_3.R");
		this.rock = getOrLogException(this.parts, "Rock");

  }
}
