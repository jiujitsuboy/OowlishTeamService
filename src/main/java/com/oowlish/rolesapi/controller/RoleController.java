package com.oowlish.rolesapi.controller;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import com.oowlish.rolesapi.hateoas.RolRepresentationModelAssembler;
import com.oowlish.rolesapi.hateoas.UserRolRepresentationModelAssembler;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@Api(value = "User Controller")
public class RoleController {

  private final RoleService service;
  private final RolRepresentationModelAssembler rolAssembler;
  private final UserRolRepresentationModelAssembler userRolAssembler;

  public RoleController(RoleService service, RolRepresentationModelAssembler rolAssembler, UserRolRepresentationModelAssembler userRolAssembler) {
    this.service = service;
    this.rolAssembler = rolAssembler;
    this.userRolAssembler = userRolAssembler;
  }

  @ApiOperation(value = "Assign Role to User", nickname = "assignRole", notes = "Assign an preexisting Role to User")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Assigned rol to user."),
      @ApiResponse(code = 500, message = "No Such Player Exception.") })
  @PatchMapping(value = "/")
  public ResponseEntity<UserRole> assignRole(@Valid @RequestBody(required = true) UserRole userRol) {
    return status(HttpStatus.ACCEPTED).body(userRolAssembler.toModel(service.assignRole(userRol)));
  }

  @ApiOperation(value = "Create Role", nickname = "createRole", notes = "Create a new team role.")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Created new role."),
      @ApiResponse(code = 406, message = "Rol already exists exception .") })
  @PostMapping(value = "/")
  public ResponseEntity<Role> createRole(@Valid @RequestBody(required = true) Role rol) {
    return status(HttpStatus.CREATED).body(rolAssembler.toModel(service.create(rol)));
  }

  @ApiOperation(value = "Get Role", nickname = "getRole", notes = "Retrieve a specific Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players."),
      @ApiResponse(code = 404, message = "No Such element exception .")})
  @GetMapping(value = "/{roleId}")
  public ResponseEntity<Role> getRole(@PathVariable("roleId") Long roleId) {
      return ok(rolAssembler.toModel(service.getRole(roleId)));
  }

  @ApiOperation(value = "Get role by membership", nickname = "getRoleByMembership", notes = "Get a role searching by userId and TeamId (Membership)")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players."),
      @ApiResponse(code = 404, message = "No Such element exception .")})
  @GetMapping(value = "/{userId}/{teamId}")
  public ResponseEntity<UserRole> getRoleByMembership(@PathVariable("userId") String userId,@PathVariable("teamId") String teamId ) {
      return ok(userRolAssembler.toModel(service.getAssignedRole(userId, teamId)));
  }

  @ApiOperation(value = "Get All Roles", nickname = "getRoles", notes = "Retrieve all Roles-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/")
  public ResponseEntity<List<Role>> getRoles() {
      return ok(rolAssembler.toListModel(service.getRoles()));
  }

  @ApiOperation(value = "Get all memberships from role", nickname = "getMemberships", notes = "Get all memberships with the specified role")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/membership/{role}")
  public ResponseEntity<List<UserRole>> getMemberships(@PathVariable("role") String role) {
      return ok(userRolAssembler.toListModel(service.getMemberShips(role)));
  }
}
