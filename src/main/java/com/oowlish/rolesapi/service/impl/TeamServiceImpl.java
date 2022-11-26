package com.oowlish.rolesapi.service.impl;

import com.oowlish.rolesapi.model.Team;
import com.oowlish.rolesapi.model.Member;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.service.RoleService;
import com.oowlish.rolesapi.service.TeamService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TeamServiceImpl implements TeamService {

  private RestTemplate restTemplate;
  private RoleService rolService;

  public TeamServiceImpl(RestTemplate restTemplate, RoleService rolService) {
    this.restTemplate = restTemplate;
    this.rolService = rolService;
  }

  @Override
  public Team getTeam(String teamId) {

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Team> response = restTemplate.exchange(
        String.format("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/%s", teamId),
        HttpMethod.GET, entity, Team.class);

    Team team = response.getBody();

    List<Member> members = team.getMembers().stream().map(member -> {
      try {
        UserRole userRol = rolService.getAssignedRole(member.getId(), team.getId());
        member.setRol(userRol.getRol().getName());
      } catch (NoSuchElementException ex) {
        member.setRol("Developer");
      }
      return member;
    }).collect(Collectors.toList());

    team.setMembers(members);

    return team;
  }

  @Override
  public JSONObject[] getTeams() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<JSONObject[]> response = restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/",
        HttpMethod.GET, entity, JSONObject[].class);

    return response.getBody();
  }
}
