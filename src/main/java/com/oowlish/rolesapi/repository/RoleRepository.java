package com.oowlish.rolesapi.repository;

import com.oowlish.rolesapi.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByName(String name);
}
