package com.oowlish.rolesapi.repository;

import com.oowlish.rolesapi.entity.UserRolEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRolRepository extends CrudRepository<UserRolEntity, Long> {
  Optional<UserRolEntity> findUserRolEntityByIdUserAndIdTeam(String idUser, String idTeam);
  List<UserRolEntity> findUserRolEntityByRolName(String rolName);
}
