package com.oowlish.rolesapi.service.impl;

import com.oowlish.rolesapi.exception.RolAlreadyExistsException;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.entity.RolEntity;
import com.oowlish.rolesapi.entity.UserRolEntity;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.repository.RolRepository;
import com.oowlish.rolesapi.repository.UserRolRepository;
import com.oowlish.rolesapi.service.RolService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

  private RolRepository rolRepository;
  private UserRolRepository userRolRepository;

  public RolServiceImpl(RolRepository rolRepository, UserRolRepository userRolRepository) {
    this.rolRepository = rolRepository;
    this.userRolRepository = userRolRepository;
  }

  @Override
  public Rol getRol(long id) {
    return rolRepository.findById(id)
        .map(entity -> Rol.builder().id(entity.getId()).name(entity.getName()).build())
        .orElseThrow(() -> new NoSuchElementException(
            String.format(" Rol with id doesn't exists %s ", id)));
  }

  @Override
  public List<Rol> getRoles() {
    List<Rol> roles = new ArrayList<>();
    Iterable<RolEntity> rolEntities = rolRepository.findAll();
    rolEntities.forEach(
        entity -> roles.add(Rol.builder().id(entity.getId()).name(entity.getName()).build()));
    return roles;
  }

  @Override
  public Rol create(Rol rol) {

    if (rolRepository.findByName(rol.getName()).isPresent()) {
      throw new RolAlreadyExistsException(String.format("Rol %s already exists.", rol.getName()));
    }

    RolEntity rolEntity = RolEntity.builder().name(rol.getName()).build();
    rolEntity = rolRepository.save(rolEntity);
    rol.setId(rolEntity.getId());

    return rol;
  }

  @Override
  public UserRol assignRol(UserRol userRol) {
    UserRolEntity userRolEntity = UserRolEntity.builder()
        .idUser(userRol.getIdUser())
        .idTeam(userRol.getIdTeam())
        .rol(RolEntity.builder().id(userRol.getId()).name(userRol.getRol().getName()).build())
        .build();

    UserRolEntity userRolEntitySaved = userRolRepository.save(userRolEntity);

    userRol.setId(userRolEntitySaved.getId());

    return userRol;
  }

  @Override
  public UserRol getAssignedRol(String idUser, String idTeam) {
    return userRolRepository.findUserRolEntityByIdUserAndIdTeam(idUser, idTeam)
        .map(entity -> UserRol.builder()
            .id(entity.getId())
            .idUser(entity.getIdUser())
            .idTeam(entity.getIdTeam())
            .rol(Rol.builder()
                .id(entity.getRol().getId())
                .name(entity.getRol().getName()).build())
            .build())
        .orElseThrow(() -> new NoSuchElementException(""));
  }

  @Override
  public List<UserRol> getMemberShips(String rolName) {
    var userRols = userRolRepository.findUserRolEntityByRolName(rolName);
    return userRols.stream().map(entity -> UserRol.builder()
        .id(entity.getId())
        .idUser(entity.getIdUser())
        .idTeam(entity.getIdTeam())
        .rol(Rol.builder()
            .id(entity.getRol().getId())
            .name(entity.getRol().getName()).build())
        .build()).collect(Collectors.toList());
  }
}
