package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.model.UserRole;
import java.util.List;

/**
 * Rol available operations
 */
public interface RoleService {

  Role getRole(long id);
  List<Role> getRoles();

  Role create(Role role);

  UserRole assignRole(UserRole userRol);

  UserRole getAssignedRole(String idUser, String idTeam);

  List<UserRole> getMemberShips(String rolName);

}
