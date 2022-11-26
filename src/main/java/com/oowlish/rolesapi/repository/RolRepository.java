package com.oowlish.rolesapi.repository;

import com.oowlish.rolesapi.entity.RolEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends CrudRepository<RolEntity, Long> {
  Optional<RolEntity> findByName(String name);
}
