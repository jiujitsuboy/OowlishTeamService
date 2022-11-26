package com.oowlish.rolesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oowlish.rolesapi.entity.RoleEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUser extends RepresentationModel<SystemUser> {

  @JsonProperty("id")
  private UUID id;
  @JsonProperty("username")
  private String username;
  @JsonProperty("firstName")
  private String firstName;
  @JsonProperty("lastName")
  private String lastName;
  @JsonProperty("email")
  private String email;
  @JsonProperty("password")
  private String password;
  @JsonProperty("role")
  private RoleEnum role;

}
