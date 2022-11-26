package com.oowlish.rolesapi.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.oowlish.rolesapi.hateoas.TeamRepresentationModelAssembler;
import com.oowlish.rolesapi.model.Team;
import com.oowlish.rolesapi.service.TeamService;
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
@Api(value = "Team Controller")
public class TeamController {

  private final TeamService service;
  private final TeamRepresentationModelAssembler teamAssembler;

  public TeamController(TeamService service, TeamRepresentationModelAssembler teamAssembler) {
    this.teamAssembler = teamAssembler;
    this.service = service;
  }

  @ApiOperation(value = "Get Team", nickname = "getTeam", notes = "Retrieve a single team identified by the specified user ID-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve the specified team.")})
  @GetMapping(value = "/teams/{teamId}")
  public ResponseEntity<Team> getTeam(@PathVariable("teamId") String teamId) {

    return ok(teamAssembler.toModel(service.getTeam(teamId)));
  }

  @ApiOperation(value = "Get All Teams", nickname = "getAllTeams", notes = "Retrieve all the teams-")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retrieve all the team.")})
  @GetMapping(value = "/teams")
  public ResponseEntity<JSONObject[]> getAllTeams() {
    return ok(service.getTeams());
  }
}
