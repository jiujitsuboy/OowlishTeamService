package com.oowlish.rolesapi.exception;

public class RolAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RolAlreadyExistsException(final String message) {
    super(message);
  }
}
