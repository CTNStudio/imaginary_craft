package ctn.imaginarycraft.api.epicfight;

import yesman.epicfight.api.animation.*;

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
