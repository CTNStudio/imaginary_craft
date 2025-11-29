package ctn.imaginarycraft.client.particle;

import javax.annotation.Nullable;

public enum TextParticlesType {
  PHYSICS(0,"damage_type/physics"),
  SPIRIT(1,"damage_type/spirit"),
  EROSION(2,"damage_type/erosion"),
  THE_SOUL(3,"damage_type/the_soul"),
  RATIONALITY_ADD(4,"damage_type/rationality_add"),
  RATIONALITY_REDUCE(5,"damage_type/rationality_reduce"),
  MAGIC(6,"damage_type/magic");

  private final int index;
  private final @Nullable String texturePl;

  TextParticlesType(final int index, @Nullable final String texturePl) {
    this.index = index;
    this.texturePl = texturePl;
  }

  public  @Nullable String getTexturePl() {
    return texturePl;
  }

  public int getIndex() {
    return index;
  }
}
