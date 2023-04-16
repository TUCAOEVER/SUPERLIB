package com.tucaoever.superlib.addons.skriptmirror;

public class Null {
  private static final Null instance = new Null();

  private Null() {
  }

  public static Null getInstance() {
    return instance;
  }

}
