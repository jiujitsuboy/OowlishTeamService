package com.oowlish.rolesapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oowlish.rolesapi.configuration.AppConfig;
import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.hateoas.RolRepresentationModelAssembler;
import com.oowlish.rolesapi.hateoas.UserRolRepresentationModelAssembler;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.UserRol;
import com.oowlish.rolesapi.security.JwtManager;
import com.oowlish.rolesapi.TestConstants;
import com.oowlish.rolesapi.service.RolService;
import java.util.List;
import java.util.UUID;
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
@WebMvcTest(RolController.class)
@Import(AppConfig.class)
@ComponentScan(basePackages = "com.oowlish.rolesapi.security")
class RolControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper json;
  @Autowired
  private JwtManager tokenManager;
  @MockBean
  private RolService service;
  @SpyBean
  private RolRepresentationModelAssembler rolAssembler;
  @SpyBean
  private UserRolRepresentationModelAssembler userRolAssembler;

  private String token;

  @BeforeEach
  public void getToken(){
    token = TestConstants.getToken(tokenManager, RoleEnum.USER.name());
  }

  @Test
  public void assignRol() throws Exception{

    String userId = UUID.randomUUID().toString();
    String teamId = UUID.randomUUID().toString();
    String rolName = "Developer";

    Rol rol = Rol.builder().id(1L).name(rolName).build();
    UserRol userRol = UserRol.builder().id(1L).idUser(userId).idTeam(teamId).rol(rol).build();

    when(service.assignRol(any(UserRol.class))).thenReturn(userRol);

    mvc.perform(patch("/api/v1/rol/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(userRol))
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.idTeam", is(userRol.getIdTeam())))
        .andExpect(jsonPath("$.idUser", is(userRol.getIdUser())));
  }

  @Test
  public void createRol() throws Exception{

    String rolName = "Developer";

    Rol rol = Rol.builder().id(1L).name(rolName).build();

    when(service.create(any(Rol.class))).thenReturn(rol);

    mvc.perform(post("/api/v1/rol/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(rol))
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(rol.getName())));
  }

  @Test
  public void getRol() throws Exception{

    Long rolId = 1L;

    String rolName = "Developer";

    Rol rol = Rol.builder().id(rolId).name(rolName).build();

    when(service.getRol(anyLong())).thenReturn(rol);

    mvc.perform(get("/api/v1/rol/{rolId}",rolId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(rolId.intValue())))
        .andExpect(jsonPath("$.name", is(rol.getName())));
  }

  @Test
  public void getRolByMembership() throws Exception{

    String userId = UUID.randomUUID().toString();
    String teamId = UUID.randomUUID().toString();
    String rolName = "Developer";

    Rol rol = Rol.builder().id(1L).name(rolName).build();
    UserRol userRol = UserRol.builder().id(1L).idUser(userId).idTeam(teamId).rol(rol).build();

    when(service.getAssignedRol(anyString(), anyString())).thenReturn(userRol);

    mvc.perform(get("/api/v1/rol/{userId}/{teamId}", userId, teamId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.idTeam", is(userRol.getIdTeam())))
        .andExpect(jsonPath("$.idUser", is(userRol.getIdUser())));
  }

  @Test
  public void getRoles() throws Exception{

    String rolName1 = "Developer";
    String rolName2 = "Product Owner";

    List<Rol> roles = List.of(Rol.builder().id(1L).name(rolName1).build(),
        Rol.builder().id(2L).name(rolName2).build());

    when(service.getRoles()).thenReturn(roles);

    mvc.perform(get("/api/v1/rol/")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(roles.get(0).getId().intValue())))
        .andExpect(jsonPath("$[0].name", is(roles.get(0).getName())));
  }

  @Test
  public void getMemberships() throws Exception{

    String rolName = "Developer";
    String userId = UUID.randomUUID().toString();
    String teamId1 = UUID.randomUUID().toString();
    String teamId2 = UUID.randomUUID().toString();

    Rol rol = Rol.builder().id(1L).name(rolName).build();

    List<UserRol> userRoles = List.of(UserRol.builder().id(1L).idUser(userId).idTeam(teamId1).rol(rol).build(),
        UserRol.builder().id(2L).idUser(userId).idTeam(teamId2).rol(rol).build());

    when(service.getMemberShips(anyString())).thenReturn(userRoles);

    mvc.perform(get("/api/v1/rol/membership/{rol}", rolName)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id", is(userRoles.get(0).getId().intValue())))
        .andExpect(jsonPath("$[0].idTeam", is(userRoles.get(0).getIdTeam())))
        .andExpect(jsonPath("$[0].idUser", is(userRoles.get(0).getIdUser())));
  }

}