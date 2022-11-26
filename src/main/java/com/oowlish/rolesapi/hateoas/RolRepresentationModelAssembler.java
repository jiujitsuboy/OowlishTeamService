package com.oowlish.rolesapi.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.RolController;
import com.oowlish.rolesapi.entity.RolEntity;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.service.Util;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RolRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Rol, Rol> {

  public RolRepresentationModelAssembler() {
    super(RolController.class, Rol.class);
  }

  @Override
  public Rol toModel(Rol entity) {
    Rol rol= (Rol) Util.toModel(entity);
    rol.add(linkTo(methodOn(RolController.class).getRol(rol.getId())).withRel("rol"));
    return rol;
  }

  public List<Rol> toListModel(Iterable<Rol> entities) {
    if (Objects.isNull(entities)) {
      return Collections.emptyList();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
        .collect(toList());
  }

}
