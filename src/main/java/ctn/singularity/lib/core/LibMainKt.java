package ctn.singularity.lib.core;

import ctn.singularity.lib.AppMainKt;

/**
 * 执行入口。这部分与模组无关，用于处理一些模组外，开发时会用到的工具。
 */
public final class LibMainKt {
  public static void main(String[] args) {
    // 执行 Kotlin 入口。
    AppMainKt.mainKt();
  }
}
