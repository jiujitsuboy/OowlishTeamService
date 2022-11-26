package com.oowlish.rolesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.oowlish.rolesapi.entity.RolEntity;
import com.oowlish.rolesapi.entity.UserRolEntity;
import com.oowlish.rolesapi.exception.RolAlreadyExistsException;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.repository.RolRepository;
import com.oowlish.rolesapi.repository.UserRolRepository;
import com.oowlish.rolesapi.service.impl.RolServiceImpl;
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
class RolServiceTest {

  @Mock
  private RolRepository rolRepository;
  @Mock
  private UserRolRepository userRolRepository;

  @InjectMocks
  private RolServiceImpl classUnderTest;


  @Test
  public void getRol(){
    Long rolId = 1L;

    RolEntity rolEntity = RolEntity.builder().id(1L).name("Developer").build();

    when(rolRepository.findById(anyLong())).thenReturn(Optional.of(rolEntity));

    Rol rol = classUnderTest.getRol(rolId);
    assertNotNull(rol);
    assertEquals(rolEntity.getId(),rol.getId());
    assertEquals(rolEntity.getName(),rol.getName());

  }

  @Test
  public void getRolNoSuchElementException(){
    Long rolId = 10L;

    when(rolRepository.findById(anyLong())).thenReturn(Optional.empty());;

    assertThrows(NoSuchElementException.class,()->classUnderTest.getRol(rolId));
  }

  @Test
  public void getRoles(){

    List<RolEntity> rolEntities = List.of(RolEntity.builder().id(1L).name("Developer").build(),
        RolEntity.builder().id(2L).name("Product Owner").build());

    when(rolRepository.findAll()).thenReturn(rolEntities);

    List<Rol> roles = classUnderTest.getRoles();
    for(int index=0;index<roles.size();index++){
      assertNotNull(roles.get(index));
      assertEquals(rolEntities.get(index).getId(),roles.get(index).getId());
      assertEquals(rolEntities.get(index).getName(),roles.get(index).getName());
    }
  }

  @Test
  public void createRol(){
    RolEntity rolEntity = RolEntity.builder().id(1L).name("Developer").build();

    Rol rol = Rol.builder().name("Developer").build();

    when(rolRepository.findByName(anyString())).thenReturn(Optional.empty());
    when(rolRepository.save(any(RolEntity.class))).thenReturn(rolEntity);

    Rol rolCreated = classUnderTest.create(rol);

    assertNotNull(rolCreated);
    assertEquals(rolEntity.getId(),rolCreated.getId());
    assertEquals(rolEntity.getName(),rolCreated.getName());

  }

  @Test
  public void createRolAlreadyExistsException(){
    RolEntity rolEntity = RolEntity.builder().id(1L).name("Developer").build();
    Rol rol = Rol.builder().name("Developer").build();

    when(rolRepository.findByName(anyString())).thenReturn(Optional.of(rolEntity));

    assertThrows(RolAlreadyExistsException.class,()->classUnderTest.create(rol));

  }

  @Test
  public void assignRol(){
    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    Rol rol = Rol.builder().name("Tester").build();
    RolEntity rolEntity = RolEntity.builder().id(3L).name("Tester").build();

    UserRol userRol = UserRol.builder().idUser(idUser).idTeam(idTeam).rol(rol).build();
    UserRolEntity userRolEntity = UserRolEntity.builder().id(1L).idUser(idUser).idTeam(idTeam).rol(rolEntity).build();

    when(userRolRepository.save(any(UserRolEntity.class))).thenReturn(userRolEntity);

    UserRol userRolCreated = classUnderTest.assignRol(userRol);

    assertNotNull(userRolCreated);
    assertNotNull(userRolCreated.getId());
    assertEquals(userRolEntity.getIdUser(),userRolCreated.getIdUser());
    assertEquals(userRolEntity.getIdTeam(),userRolCreated.getIdTeam());

  }

  @Test
  public void getAssignedRol(){

    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    Rol rol = Rol.builder().name("Tester").build();
    RolEntity rolEntity = RolEntity.builder().id(3L).name("Tester").build();

    UserRol userRol = UserRol.builder().idUser(idUser).idTeam(idTeam).rol(rol).build();
    UserRolEntity userRolEntity = UserRolEntity.builder().id(1L).idUser(idUser).idTeam(idTeam).rol(rolEntity).build();

    when(userRolRepository.findUserRolEntityByIdUserAndIdTeam(anyString(), anyString())).thenReturn(Optional.of(userRolEntity));

    UserRol userRolRetrieved = classUnderTest.getAssignedRol(idUser, idTeam);

    assertNotNull(userRolRetrieved);
    assertNotNull(userRolRetrieved.getId());
    assertEquals(userRolEntity.getIdUser(),userRolRetrieved.getIdUser());
    assertEquals(userRolEntity.getIdTeam(),userRolRetrieved.getIdTeam());
  }

  @Test
  public void getAssignedRolNoSuchElementException(){

    String idUser = UUID.randomUUID().toString();
    String idTeam = UUID.randomUUID().toString();

    when(userRolRepository.findUserRolEntityByIdUserAndIdTeam(anyString(), anyString())).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class,()->classUnderTest.getAssignedRol(idUser, idTeam));
  }

  @Test
  public void getMemberShips(){

    String rolName = "Tester";
    String idUser = UUID.randomUUID().toString();
    String idTeam1 = UUID.randomUUID().toString();
    String idTeam2 = UUID.randomUUID().toString();

    RolEntity rolEntity1 = RolEntity.builder().id(1L).name("Developer").build();
    RolEntity rolEntity2 = RolEntity.builder().id(3L).name("Tester").build();

    List<UserRolEntity> usersRolEntity = List.of(UserRolEntity.builder().id(1L).idUser(idUser).idTeam(idTeam1).rol(rolEntity1).build(),
        UserRolEntity.builder().id(2L).idUser(idUser).idTeam(idTeam2).rol(rolEntity2).build());

    when(userRolRepository.findUserRolEntityByRolName(anyString())).thenReturn(usersRolEntity);

    List<UserRol> usersRol = classUnderTest.getMemberShips(rolName);

    assertTrue(usersRol.size()>0);
  }

}