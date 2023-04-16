package com.tucaoever.superlib.addons.skriptmirror.skript.custom;

public interface Continuable {

  default void markContinue() {
    setContinue(true);
  }

  void setContinue(boolean b);

}
