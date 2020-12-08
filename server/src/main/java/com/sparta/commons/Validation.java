package com.sparta.commons;

public final class Validation {

  private Validation() {
  }

  public static void checkNotNull(Object value, String message) {
    if (value == null) {
      throw new IllegalArgumentException(message);
    }
  }

}
