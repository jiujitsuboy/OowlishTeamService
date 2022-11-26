package com.oowlish.rolesapi.exception;

public class NoSuchTeamException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NoSuchTeamException(final String message) {
    super(message);
  }
}
