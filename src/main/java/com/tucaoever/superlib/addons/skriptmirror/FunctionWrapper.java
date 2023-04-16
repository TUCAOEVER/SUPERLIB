package com.tucaoever.superlib.addons.skriptmirror;

import ch.njol.skript.lang.function.Function;
import ch.njol.skript.lang.function.Functions;

import javax.annotation.Nullable;

public class FunctionWrapper {

  private final String name;
  private final Object[] arguments;

  public FunctionWrapper(String name, Object[] arguments) {
    this.name = name;
    this.arguments = arguments;
  }

  public String getName() {
    return name;
  }

  public Object[] getArguments() {
    return arguments;
  }

  @Nullable
  public Function<?> getFunction() {
    return Functions.getFunction(name);
  }

}

