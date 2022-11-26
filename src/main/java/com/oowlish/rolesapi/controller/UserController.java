package com.oowlish.rolesapi.controller;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import com.oowlish.rolesapi.service.User2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(value = "User Controller")
public class UserController {

  private final User2Service service;

  public UserController(User2Service service) {
    this.service = service;
  }

  @ApiOperation(value = "Get user", nickname = "getUser", notes = "Retrieve a single user identified by the specified ID-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve the specified user."),
      @ApiResponse(code = 400, message = "No Such Player Exception.") })
  @GetMapping(value = "/users/{userId}")
  public ResponseEntity<JSONObject> getUser(@PathVariable("userId") String userId) {
    return ok(service.getUser(userId));
  }

  @ApiOperation(value = "Get All users", nickname = "getAllUsers", notes = "Retrieve all users-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all players.")})
  @GetMapping(value = "/users")
  public ResponseEntity<JSONObject[]> getAllUsers() {
    return ok(service.getUsers());
  }
}
