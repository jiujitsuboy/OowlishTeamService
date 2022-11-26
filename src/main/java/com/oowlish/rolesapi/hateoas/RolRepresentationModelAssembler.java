package com.oowlish.rolesapi.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.RoleController;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.service.Util;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RolRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Role, Role> {

  public RolRepresentationModelAssembler() {
    super(RoleController.class, Role.class);
  }

  @Override
  public Role toModel(Role entity) {
    Role rol= (Role) Util.toModel(entity);
    rol.add(linkTo(methodOn(RoleController.class).getRole(rol.getId())).withRel("rol"));
    return rol;
  }

  public List<Role> toListModel(Iterable<Role> entities) {
    if (Objects.isNull(entities)) {
      return Collections.emptyList();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
        .collect(toList());
  }

}
