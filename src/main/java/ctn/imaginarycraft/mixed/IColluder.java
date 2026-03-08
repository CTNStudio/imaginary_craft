package ctn.imaginarycraft.mixed;

import yesman.epicfight.api.collider.*;

public interface IColluder {
  static IColluder of(Collider o) {
    return (IColluder) o;
  }
}
