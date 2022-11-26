package com.oowlish.rolesapi.repository;

import com.oowlish.rolesapi.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

  Optional<UserEntity> findByUsername(String username);
  Integer countByUsernameOrEmail(String username, String email);
}
