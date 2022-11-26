package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.UserRol;
import java.util.List;

/**
 * Player available operations
 */
public interface RolService {

  /**
   * Return the player identified by the Id
   * @param id user's id
   * @return @link{PlayerEntity}
   */
  Rol getRol(long id);
  List<Rol> getRoles();

  Rol create(Rol rol);

  UserRol assignRol(UserRol userRol);

  UserRol getAssignedRol(String idUser, String idTeam);

  List<UserRol> getMemberShips(String rolName);

}
