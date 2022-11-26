package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.entity.RolEntity;
import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.entity.UserRolEntity;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.User;
import com.oowlish.rolesapi.model.User2;
import com.oowlish.rolesapi.model.UserRol;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class Util {

  public static final List<String> COUNTRIES = List.of("Colombia", "Argentina", "Brazil", "Chile");
  public static final List<String> NAMES = List.of("Patrick Mahomes",
      "Tom Brady",
      "Ezekiel Elliott",
      "Saquon Barkley",
      "Aaron Rodgers",
      "Drew Brees",
      "Todd Gurley",
      "Khalil Mack");

  public static <M> Object toEntity(M model) {
    Object entity = null;

    if (model instanceof User) {
      entity = new UserEntity();
    } else if (model instanceof User2) {
      entity = new UserEntity();
    } else if (model instanceof Rol) {
      entity = new RolEntity();
    } else if (model instanceof UserRol) {
      entity = new UserRolEntity();
    }
    BeanUtils.copyProperties(model, entity);
    return entity;
  }

  public static <E> Object toModel(E entity) {
    Object model = null;
    if (entity instanceof UserEntity) {
      model = new User();
    } else if (entity instanceof UserRol) {
      model = new UserRol();
    } else if (entity instanceof Rol) {
      model = new Rol();
    }

    BeanUtils.copyProperties(entity, model);
    return model;
  }
}
