package com.oowlish.rolesapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.model.SystemUser;
import com.oowlish.rolesapi.service.Util;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByUsername(){

    UUID userUUID = UUID.randomUUID();
    String firstName = "Gianni";
    String lastName = "Wehner";
    String userName = "gianniWehner";
    String email = "gianni@email.com";

    SystemUser user = SystemUser.builder().id(userUUID).firstName(firstName).lastName(lastName).username(userName).email(email).role(RoleEnum.USER).build();

    UserEntity userEntity = (UserEntity) Util.toEntity(user);
    userEntity.setPassword("mypassword");
    userRepository.save(userEntity);

    entityManager.persist(userEntity);
    entityManager.flush();

    Optional<UserEntity> optUserEntity =  userRepository.findByUsername(userName);

    assertTrue(optUserEntity.isPresent());
    assertEquals(optUserEntity.get().getFirstName(),firstName);
    assertEquals(optUserEntity.get().getLastName(),lastName);
  }

}