package com.oowlish.rolesapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole extends RepresentationModel<UserRole> {
  private Long id;
  private String idTeam;
  private String idUser;
  private Role rol;
}
