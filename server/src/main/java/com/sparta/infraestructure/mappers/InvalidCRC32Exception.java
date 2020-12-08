package com.sparta.infraestructure.mappers;

public class InvalidCRC32Exception extends RuntimeException {

  public InvalidCRC32Exception(String message) {
    super(message);
  }

}
