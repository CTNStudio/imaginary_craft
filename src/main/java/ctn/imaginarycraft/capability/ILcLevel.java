package ctn.imaginarycraft.capability;

import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ILcLevel {
  @NotNull
  LcLevel getLcLevel();
}
