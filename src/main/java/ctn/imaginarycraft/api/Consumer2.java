package ctn.imaginarycraft.api;

import java.util.Objects;

public interface Consumer2<T, U> {
  void accept(T t, U u);

  default Consumer2<T, U> andThen(Consumer2<? super T, ? super U> after) {
    Objects.requireNonNull(after);
    return (T t, U u) -> {
      accept(t, u);
      after.accept(t, u);
    };
  }
}
