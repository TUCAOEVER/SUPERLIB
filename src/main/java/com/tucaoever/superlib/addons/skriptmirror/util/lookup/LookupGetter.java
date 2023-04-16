package com.tucaoever.superlib.addons.skriptmirror.util.lookup;

import java.lang.invoke.MethodHandles;

/**
 * A class and package specifically for the lookup creator,
 * so it cannot access anything it isn't supposed to.
 */
public class LookupGetter {

  public static MethodHandles.Lookup getLookup() {
    return MethodHandles.lookup();
  }

}
