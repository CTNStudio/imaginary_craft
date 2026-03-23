package ctn.imaginarycraft.init.animmodels;

import ctn.imaginarycraft.client.model.animmodels.mesh.GrantUsLoveMesh;
import ctn.imaginarycraft.core.ImaginaryCraft;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.client.model.VertexBuilder;

/**
 * 网格（模型）
 */
public final class ModMeshes {
  public static final Meshes.MeshAccessor<GrantUsLoveMesh> GRANT_US_LOVE = create("entity/grant_us_love", GrantUsLoveMesh::new);

  public static void init() {
  }

  private static <T extends SkinnedMesh> Meshes.MeshAccessor<T> create(String name, Meshes.MeshContructor<SkinnedMesh.SkinnedMeshPart, VertexBuilder, T> constructor) {
    return Meshes.MeshAccessor.create(ImaginaryCraft.ID, name, (jsonModelLoader) -> jsonModelLoader.loadSkinnedMesh(constructor));
  }
}
