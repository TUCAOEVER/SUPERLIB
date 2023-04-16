package com.tucaoever.superlib.addons.skriptmirror;

public class JavaCallException extends RuntimeException {
  public JavaCallException(String message) {
    super(message);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
