package com.oowlish.rolesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User extends RepresentationModel<User> {

  @JsonProperty("id")
  private UUID id;
  @JsonProperty("firstName")
  private String firstName;
  @JsonProperty("lastName")
  private String lastName;
  @JsonProperty("displayName")
  private String displayName;
  @JsonProperty("avatarUrl")
  private String avatarUrl;
  @JsonProperty("location")
  private String location;
  @JsonProperty("role")
  private String role;

}
