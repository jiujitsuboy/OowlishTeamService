package com.oowlish.rolesapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
  USER(Const.USER),

  ADMIN(Const.ADMIN);

  private String authority;

  RoleEnum(String authority) {
    this.authority = authority;
  }

  @JsonCreator
  public static RoleEnum fromAuthority(String authority) {
    for (RoleEnum b : RoleEnum.values()) {
      if (b.authority.equals(authority)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + authority + "'");
  }

  @Override
  public String toString() {
    return String.valueOf(authority);
  }

  @Override
  @JsonValue
  public String getAuthority() {
    return authority;
  }

  public class Const {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
  }
}
