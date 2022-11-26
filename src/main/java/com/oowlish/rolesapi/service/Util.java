package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.entity.RoleEntity;
import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.entity.UserRoleEntity;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.model.SystemUser;
import com.oowlish.rolesapi.model.User;
import com.oowlish.rolesapi.model.UserRole;
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

    if (model instanceof SystemUser) {
      entity = new UserEntity();
    } else if (model instanceof User) {
      entity = new UserEntity();
    } else if (model instanceof Role) {
      entity = new RoleEntity();
    } else if (model instanceof UserRole) {
      entity = new UserRoleEntity();
    }
    BeanUtils.copyProperties(model, entity);
    return entity;
  }

  public static <E> Object toModel(E entity) {
    Object model = null;
    if (entity instanceof UserEntity) {
      model = new SystemUser();
    } else if (entity instanceof UserRole) {
      model = new UserRole();
    } else if (entity instanceof Role) {
      model = new Role();
    }

    BeanUtils.copyProperties(entity, model);
    return model;
  }
}
