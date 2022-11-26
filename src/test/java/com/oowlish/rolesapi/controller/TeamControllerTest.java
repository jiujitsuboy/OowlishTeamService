package com.oowlish.rolesapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.oowlish.rolesapi.TestConstants;
import com.oowlish.rolesapi.configuration.AppConfig;
import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.hateoas.TeamRepresentationModelAssembler;
import com.oowlish.rolesapi.model.Member;
import com.oowlish.rolesapi.model.Team;
import com.oowlish.rolesapi.security.JwtManager;
import com.oowlish.rolesapi.service.TeamService;
import java.util.List;
import java.util.UUID;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeamController.class)
@Import(AppConfig.class)
@ComponentScan(basePackages = "com.oowlish.rolesapi.security")
class TeamControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper json;
  @Autowired
  private JwtManager tokenManager;
  @MockBean
  private TeamService service;
  @SpyBean
  private TeamRepresentationModelAssembler teamAssembler;

  private String token;

  @BeforeEach
  public void getToken(){
    token = TestConstants.getToken(tokenManager, RoleEnum.USER.name());
  }

  @Test
  public void getTeam() throws Exception{
    String userUUID1 = UUID.randomUUID().toString();
    String userUUID2 = UUID.randomUUID().toString();
    String teamUserUUID = UUID.randomUUID().toString();
    String teamUUID = UUID.randomUUID().toString();
    String rolName = "Developer";
    String teamName = "Team1";

    List<Member> members = List.of(Member.builder().id(userUUID1).rol(rolName).build(),
        Member.builder().id(userUUID2).rol(rolName).build());

    Team team = Team.builder().id(teamUUID).teamLeadId(teamUserUUID).name(teamName).members(members).build();

    when(service.getTeam(anyString())).thenReturn(team);

    mvc.perform(get("/api/v1/teams/{teamId}", teamUUID)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(team.getId())))
        .andExpect(jsonPath("$.teamLeadId", is(team.getTeamLeadId())))
        .andExpect(jsonPath("$.name", is(team.getName())))
        .andExpect(jsonPath("$.members", hasSize(team.getMembers().size())));
  }

  @Test
  public void getAllTeams() throws Exception{

    String userUUID1 = UUID.randomUUID().toString();
    String userUUID2 = UUID.randomUUID().toString();
    String teamUserUUID = UUID.randomUUID().toString();
    String teamUUID1 = UUID.randomUUID().toString();
    String teamUUID2 = UUID.randomUUID().toString();
    String rolName = "Developer";
    String teamName = "Team1";

    List<Member> members = List.of(Member.builder().id(userUUID1).rol(rolName).build(),
        Member.builder().id(userUUID2).rol(rolName).build());

    List<Team> teams = List.of(Team.builder().id(teamUUID1).teamLeadId(teamUserUUID).name(teamName).members(members).build(),
        Team.builder().id(teamUUID2).teamLeadId(teamUserUUID).name(teamName).members(members).build());

    JSONObject[] jsonObjects = new JSONObject[2];

    for(int index=0;index<jsonObjects.length;index++){
      JSONObject jsonObject = new JSONObject();
      jsonObject.appendField("id", teams.get(index).getId());
      jsonObject.appendField("teamLeadId", teams.get(index).getTeamLeadId());
      jsonObject.appendField("name", teams.get(index).getName());
      jsonObject.appendField("members", teams.get(index).getMembers());

      jsonObjects[index] =  jsonObject;
    }

    when(service.getTeams()).thenReturn(jsonObjects);

    mvc.perform(get("/api/v1/teams/")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(teams.get(0).getId())))
        .andExpect(jsonPath("$[0].teamLeadId", is(teams.get(0).getTeamLeadId())))
        .andExpect(jsonPath("$[0].name", is(teams.get(0).getName())))
        .andExpect(jsonPath("$[0].members", hasSize(teams.get(0).getMembers().size())));
  }

}