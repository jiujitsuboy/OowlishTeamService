package com.oowlish.rolesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.oowlish.rolesapi.entity.RoleEntity;
import com.oowlish.rolesapi.entity.UserRoleEntity;
import com.oowlish.rolesapi.exception.RolAlreadyExistsException;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.repository.RoleRepository;
import com.oowlish.rolesapi.repository.UserRoleRepository;
import com.oowlish.rolesapi.service.impl.RoleServiceImpl;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  @Mock
  private RoleRepository rolRepository;
  @Mock
  private UserRoleRepository userRolRepository;

  @InjectMocks
  private RoleServiceImpl classUnderTest;


  @Test
  public void getRol(){
    Long rolId = 1L;

    RoleEntity rolEntity = RoleEntity.builder().id(1L).name("Developer").build();

    when(rolRepository.findById(anyLong())).thenReturn(Optional.of(rolEntity));

    Role rol = classUnderTest.getRole(rolId);
    assertNotNull(rol);
    assertEquals(rolEntity.getId(),rol.getId());
    assertEquals(rolEntity.getName(),rol.getName());

  }

  @Test
  public void getRolNoSuchElementException(){
    Long rolId = 10L;

    when(rolRepository.findById(anyLong())).thenReturn(Optional.empty());;

    assertThrows(NoSuchElementException.class,()->classUnderTest.getRole(rolId));
  }

  @Test
  public void getRoles(){

    List<RoleEntity> rolEntities = List.of(RoleEntity.builder().id(1L).name("Developer").build(),
        RoleEntity.builder().id(2L).name("Product Owner").build());

    when(rolRepository.findAll()).thenReturn(rolEntities);

    List<Role> roles = classUnderTest.getRoles();
    for(int index=0;index<roles.size();index++){
      assertNotNull(roles.get(index));
      assertEquals(rolEntities.get(index).getId(),roles.get(index).getId());
      assertEquals(rolEntities.get(index).getName(),roles.get(index).getName());
    }
  }

  @Test
  public void createRol(){
    RoleEntity rolEntity = RoleEntity.builder().id(1L).name("Developer").build();

    Role rol = Role.builder().name("Developer").build();

    when(rolRepository.findByName(anyString())).thenReturn(Optional.empty());
    when(rolRepository.save(any(RoleEntity.class))).thenReturn(rolEntity);

    Role rolCreated = classUnderTest.create(rol);

    assertNotNull(rolCreated);
    assertEquals(rolEntity.getId(),rolCreated.getId());
    assertEquals(rolEntity.getName(),rolCreated.getName());

  }

  @Test
  public void createRolAlreadyExistsException(){
    RoleEntity rolEntity = RoleEntity.builder().id(1L).name("Developer").build();
    Role rol = Role.builder().name("Developer").build();

    when(rolRepository.findByName(anyString())).thenReturn(Optional.of(rolEntity));

    assertThrows(RolAlreadyExistsException.class,()->classUnderTest.create(rol));

  }

  @Test
  public void assignRol(){
    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    Role rol = Role.builder().name("Tester").build();
    RoleEntity rolEntity = RoleEntity.builder().id(3L).name("Tester").build();

    UserRole userRol = UserRole.builder().idUser(idUser).idTeam(idTeam).rol(rol).build();
    UserRoleEntity userRolEntity = UserRoleEntity.builder().id(1L).idUser(idUser).idTeam(idTeam).role(rolEntity).build();

    when(userRolRepository.save(any(UserRoleEntity.class))).thenReturn(userRolEntity);

    UserRole userRolCreated = classUnderTest.assignRole(userRol);

    assertNotNull(userRolCreated);
    assertNotNull(userRolCreated.getId());
    assertEquals(userRolEntity.getIdUser(),userRolCreated.getIdUser());
    assertEquals(userRolEntity.getIdTeam(),userRolCreated.getIdTeam());

  }

  @Test
  public void getAssignedRol(){

    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    Role rol = Role.builder().name("Tester").build();
    RoleEntity rolEntity = RoleEntity.builder().id(3L).name("Tester").build();

    UserRole userRol = UserRole.builder().idUser(idUser).idTeam(idTeam).rol(rol).build();
    UserRoleEntity userRolEntity = UserRoleEntity.builder().id(1L).idUser(idUser).idTeam(idTeam).role(rolEntity).build();

    when(userRolRepository.findUserRoleEntityByIdUserAndIdTeam(anyString(), anyString())).thenReturn(Optional.of(userRolEntity));

    UserRole userRolRetrieved = classUnderTest.getAssignedRole(idUser, idTeam);

    assertNotNull(userRolRetrieved);
    assertNotNull(userRolRetrieved.getId());
    assertEquals(userRolEntity.getIdUser(),userRolRetrieved.getIdUser());
    assertEquals(userRolEntity.getIdTeam(),userRolRetrieved.getIdTeam());
  }

  @Test
  public void getAssignedRolNoSuchElementException(){

    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    when(userRolRepository.findUserRoleEntityByIdUserAndIdTeam(anyString(), anyString())).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,()->classUnderTest.getAssignedRole(idUser, idTeam));
  }

  @Test
  public void getMemberShips(){

    String rolName = "Tester";
    String idUser = UUID.randomUUID().toString();
    String idTeam1 = UUID.randomUUID().toString();
    String idTeam2 = UUID.randomUUID().toString();

    RoleEntity rolEntity1 = RoleEntity.builder().id(1L).name("Developer").build();
    RoleEntity rolEntity2 = RoleEntity.builder().id(3L).name("Tester").build();

    List<UserRoleEntity> usersRolEntity = List.of(
        UserRoleEntity.builder().id(1L).idUser(idUser).idTeam(idTeam1).role(rolEntity1).build(),
        UserRoleEntity.builder().id(2L).idUser(idUser).idTeam(idTeam2).role(rolEntity2).build());

    when(userRolRepository.findUserRoleEntityByRoleName(anyString())).thenReturn(usersRolEntity);

    List<UserRole> usersRol = classUnderTest.getMemberShips(rolName);

    assertTrue(usersRol.size()>0);
  }

}