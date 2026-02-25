package ctn.imaginarycraft.api;

import yesman.epicfight.api.animation.LivingMotion;

public enum LcLivingMotion implements LivingMotion {
  ;
  private final int id;

  LcLivingMotion() {
    this.id = LivingMotion.ENUM_MANAGER.assign(this);
  }

  public int universalOrdinal() {
    return this.id;
  }
}
