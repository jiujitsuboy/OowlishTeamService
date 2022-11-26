package com.oowlish.rolesapi.exception;

public class GenericAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public GenericAlreadyExistsException(final String message) {
    super(message);
  }
}
