package com.oowlish.rolesapi.repository;

import com.oowlish.rolesapi.entity.UserRoleEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
  Optional<UserRoleEntity> findUserRoleEntityByIdUserAndIdTeam(String idUser, String idTeam);
  List<UserRoleEntity> findUserRoleEntityByRoleName(String roleName);
}
