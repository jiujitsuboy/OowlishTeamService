package com.oowlish.rolesapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oowlish.rolesapi.model.Member;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.Team;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.service.impl.TeamServiceImpl;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private RolService rolService;
  @InjectMocks
  private TeamServiceImpl classUnderTest;


  @Test
  public void getTeam() throws URISyntaxException, JsonProcessingException {

    String userUUID1 = UUID.randomUUID().toString();
    String userUUID2 = UUID.randomUUID().toString();
    String teamUserUUID = UUID.randomUUID().toString();
    String teamUUID = UUID.randomUUID().toString();
    String rolName = "Developer";
    String teamName = "Team1";

    List<Member> members = List.of(Member.builder().id(userUUID1).rol(rolName).build(),
        Member.builder().id(userUUID2).rol(rolName).build());

    Team team = Team.builder().id(teamUUID).teamLeadId(teamUserUUID).name(teamName).members(members)
        .build();

    Rol rol = Rol.builder().id(1L).name(rolName).build();
    UserRol userRol = UserRol.builder().id(1L).idUser(userUUID1).idTeam(teamUUID).rol(rol).build();

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    when(restTemplate.exchange(
        String.format("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/%s", teamUUID),
        HttpMethod.GET, entity, Team.class)).thenReturn(new ResponseEntity<>(team, HttpStatus.OK));
    when(rolService.getAssignedRol(anyString(), anyString())).thenReturn(userRol);

    Team teamRetrieved = classUnderTest.getTeam(teamUUID);

    assertNotNull(teamRetrieved);
    assertEquals(teamUUID, teamRetrieved.getId());

  }

  @Test
  public void getTeams() throws URISyntaxException, JsonProcessingException {

    String userUUID1 = UUID.randomUUID().toString();
    String userUUID2 = UUID.randomUUID().toString();
    String teamUserUUID = UUID.randomUUID().toString();
    String teamUUID1 = UUID.randomUUID().toString();
    String teamUUID2 = UUID.randomUUID().toString();
    String rolName = "Developer";
    String teamName = "Team1";

    List<Member> members = List.of(Member.builder().id(userUUID1).rol(rolName).build(),
        Member.builder().id(userUUID2).rol(rolName).build());

    List<Team> teams = List.of(
        Team.builder().id(teamUUID1).teamLeadId(teamUserUUID).name(teamName).members(members)
            .build(),
        Team.builder().id(teamUUID2).teamLeadId(teamUserUUID).name(teamName).members(members)
            .build());

    JSONObject[] jsonObjects = new JSONObject[2];

    for (int index = 0; index < jsonObjects.length; index++) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.appendField("id", teams.get(index).getId());
      jsonObject.appendField("teamLeadId", teams.get(index).getTeamLeadId());
      jsonObject.appendField("name", teams.get(index).getName());
      jsonObject.appendField("members", teams.get(index).getMembers());

      jsonObjects[index] = jsonObject;
    }

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    when(restTemplate.exchange("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/",
        HttpMethod.GET, entity, JSONObject[].class)).thenReturn(
        new ResponseEntity<>(jsonObjects, HttpStatus.OK));

    JSONObject[] teamsRetrieved = classUnderTest.getTeams();

    assertNotNull(teamsRetrieved);
    assertTrue(teamsRetrieved.length > 0);
    assertNotNull(teamsRetrieved[0].get("members"));
    assertNotNull(teamsRetrieved[0].get("name"));
    assertNotNull(teamsRetrieved[0].get("teamLeadId"));
    assertNotNull(teamsRetrieved[0].get("id"));
  }

}