package com.oowlish.rolesapi.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.RoleController;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.service.Util;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserRolRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UserRole, UserRole> {

  public UserRolRepresentationModelAssembler() {
    super(RoleController.class, UserRole.class);
  }

  @Override
  public UserRole toModel(UserRole entity) {
    UserRole userRol= (UserRole) Util.toModel(entity);
    userRol.add(linkTo(methodOn(RoleController.class).getRole(userRol.getId())).withRel("rol"));
    return userRol;
  }

  public List<UserRole> toListModel(Iterable<UserRole> entities) {
    if (Objects.isNull(entities)) {
      return Collections.emptyList();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
        .collect(toList());
  }

}
