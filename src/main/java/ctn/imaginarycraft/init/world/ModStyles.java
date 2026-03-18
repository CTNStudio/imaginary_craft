package ctn.imaginarycraft.init.world;

import yesman.epicfight.world.capabilities.item.Style;

public enum ModStyles implements Style {
  ;

  final boolean canUseOffhand;
  final int id;

  ModStyles(boolean canUseOffhand) {
    this.id = Style.ENUM_MANAGER.assign(this);
    this.canUseOffhand = canUseOffhand;
  }

  @Override
  public int universalOrdinal() {
    return this.id;
  }

  public boolean canUseOffhand() {
    return this.canUseOffhand;
  }
}
