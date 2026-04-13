package ctn.imaginarycraft.init.epicfight;

import yesman.epicfight.api.animation.LivingMotion;

public enum ModLivingMotions implements LivingMotion {
  ;

  private final int id;

  ModLivingMotions() {
    this.id = LivingMotion.ENUM_MANAGER.assign(this);
  }

  public int universalOrdinal() {
    return this.id;
  }
}
