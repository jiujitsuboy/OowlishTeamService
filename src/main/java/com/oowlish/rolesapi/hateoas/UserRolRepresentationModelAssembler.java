package com.oowlish.rolesapi.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.RolController;
import com.oowlish.rolesapi.entity.UserRolEntity;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.service.Util;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserRolRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UserRol, UserRol> {

  public UserRolRepresentationModelAssembler() {
    super(RolController.class, UserRol.class);
  }

  @Override
  public UserRol toModel(UserRol entity) {
    UserRol userRol= (UserRol) Util.toModel(entity);
    userRol.add(linkTo(methodOn(RolController.class).getRol(userRol.getId())).withRel("rol"));
    return userRol;
  }

  public List<UserRol> toListModel(Iterable<UserRol> entities) {
    if (Objects.isNull(entities)) {
      return Collections.emptyList();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
        .collect(toList());
  }

}
