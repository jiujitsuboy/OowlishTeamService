package com.oowlish.rolesapi.service.impl;

import com.oowlish.rolesapi.exception.RolAlreadyExistsException;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.entity.RoleEntity;
import com.oowlish.rolesapi.entity.UserRoleEntity;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.repository.RoleRepository;
import com.oowlish.rolesapi.repository.UserRoleRepository;
import com.oowlish.rolesapi.service.RoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;
  private UserRoleRepository userRoleRepository;

  public RoleServiceImpl(RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
    this.roleRepository = roleRepository;
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public Role getRole(long id) {
    return roleRepository.findById(id)
        .map(entity -> Role.builder().id(entity.getId()).name(entity.getName()).build())
        .orElseThrow(() -> new NoSuchElementException(
            String.format(" Role with id doesn't exists %s ", id)));
  }

  @Override
  public List<Role> getRoles() {
    List<Role> roles = new ArrayList<>();
    Iterable<RoleEntity> rolEntities = roleRepository.findAll();
    rolEntities.forEach(
        entity -> roles.add(Role.builder().id(entity.getId()).name(entity.getName()).build()));
    return roles;
  }

  @Override
  public Role create(Role role) {

    if (roleRepository.findByName(role.getName()).isPresent()) {
      throw new RolAlreadyExistsException(String.format("Role %s already exists.", role.getName()));
    }

    RoleEntity roleEntity = RoleEntity.builder().name(role.getName()).build();
    roleEntity = roleRepository.save(roleEntity);
    role.setId(roleEntity.getId());

    return role;
  }

  @Override
  public UserRole assignRole(UserRole userRole) {
    UserRoleEntity userRolEntity = UserRoleEntity.builder()
        .idUser(userRole.getIdUser())
        .idTeam(userRole.getIdTeam())
        .role(RoleEntity.builder().id(userRole.getId()).name(userRole.getRol().getName()).build())
        .build();

    UserRoleEntity userRolEntitySaved = userRoleRepository.save(userRolEntity);

    userRole.setId(userRolEntitySaved.getId());

    return userRole;
  }

  @Override
  public UserRole getAssignedRole(String idUser, String idTeam) {
    return userRoleRepository.findUserRoleEntityByIdUserAndIdTeam(idUser, idTeam)
        .map(entity -> UserRole.builder()
            .id(entity.getId())
            .idUser(entity.getIdUser())
            .idTeam(entity.getIdTeam())
            .rol(Role.builder()
                .id(entity.getRole().getId())
                .name(entity.getRole().getName()).build())
            .build())
        .orElseThrow(() -> new NoSuchElementException(""));
  }

  @Override
  public List<UserRole> getMemberShips(String rolName) {
    var userRols = userRoleRepository.findUserRoleEntityByRoleName(rolName);
    return userRols.stream().map(entity -> UserRole.builder()
        .id(entity.getId())
        .idUser(entity.getIdUser())
        .idTeam(entity.getIdTeam())
        .rol(Role.builder()
            .id(entity.getRole().getId())
            .name(entity.getRole().getName()).build())
        .build()).collect(Collectors.toList());
  }
}
