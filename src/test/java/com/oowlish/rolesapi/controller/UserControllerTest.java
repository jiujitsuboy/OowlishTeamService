package com.oowlish.rolesapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oowlish.rolesapi.TestConstants;
import com.oowlish.rolesapi.configuration.AppConfig;
import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.model.User2;
import com.oowlish.rolesapi.security.JwtManager;
import com.oowlish.rolesapi.service.User2Service;
import java.util.List;
import java.util.UUID;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(AppConfig.class)
@ComponentScan(basePackages = "com.oowlish.rolesapi.security")
class UserControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper json;
  @Autowired
  private JwtManager tokenManager;
  @MockBean
  private User2Service service;

  private String token;

  @BeforeEach
  public void getToken() {
    token = TestConstants.getToken(tokenManager, RoleEnum.USER.name());
  }

  @Test
  public void getUser() throws Exception {

    UUID userUUID = UUID.randomUUID();
    String firstName = "Gianni";
    String lastName = "Wehner";
    String displayName = "gianniWehner";
    String avatarUrl = "https://cdn.fakercloud.com/avatars/rude_128.jpg";
    String location = "Brakusstad";

    User2 user = User2.builder().id(userUUID).firstName(firstName).lastName(lastName).displayName(displayName).avatarUrl(avatarUrl).location(location).build();

    JSONObject jsonObject = new JSONObject();
    jsonObject.appendField("id", userUUID);
    jsonObject.appendField("firstName", firstName);
    jsonObject.appendField("lastName", lastName);
    jsonObject.appendField("displayName", displayName);
    jsonObject.appendField("avatarUrl", avatarUrl);
    jsonObject.appendField("location", location);

    when(service.getUser(anyString())).thenReturn(jsonObject);

    mvc.perform(get("/api/v1/users/{userId}", userUUID.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(user.getId().toString())))
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.displayName",is(user.getDisplayName())))
        .andExpect(jsonPath("$.avatarUrl", is(user.getAvatarUrl())))
        .andExpect(jsonPath("$.location", is(user.getLocation())));

  }

  @Test
  public void getAllUsers() throws Exception {

    UUID userUUID = UUID.randomUUID();
    String firstName = "Gianni";
    String lastName = "Wehner";
    String displayName = "gianniWehner";
    String avatarUrl = "https://cdn.fakercloud.com/avatars/rude_128.jpg";
    String location = "Brakusstad";

    List<User2> users = List.of(User2.builder().id(userUUID).firstName(firstName).lastName(lastName).displayName(displayName).avatarUrl(avatarUrl).location(location).build(),
    User2.builder().id(userUUID).firstName(firstName).lastName(lastName).displayName(displayName).avatarUrl(avatarUrl).location(location).build());

    JSONObject[] jsonObjects = new JSONObject[users.size()];

    for(int index=0;index<users.size();index++){
      JSONObject jsonObject = new JSONObject();
      jsonObject.appendField("id", users.get(index).getId());
      jsonObject.appendField("firstName", users.get(index).getFirstName());
      jsonObject.appendField("lastName", users.get(index).getLastName());
      jsonObject.appendField("displayName", users.get(index).getDisplayName());
      jsonObject.appendField("avatarUrl", users.get(index).getAvatarUrl());
      jsonObject.appendField("location", users.get(index).getLocation());
      jsonObjects[index]=jsonObject;
    }


    when(service.getUsers()).thenReturn(jsonObjects);

    mvc.perform(get("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION,"Bearer "+ token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(users.get(0).getId().toString())))
        .andExpect(jsonPath("$[0].firstName", is(users.get(0).getFirstName())))
        .andExpect(jsonPath("$[0].lastName", is(users.get(0).getLastName())))
        .andExpect(jsonPath("$[0].displayName",is(users.get(0).getDisplayName())))
        .andExpect(jsonPath("$[0].avatarUrl", is(users.get(0).getAvatarUrl())))
        .andExpect(jsonPath("$[0].location", is(users.get(0).getLocation())));

  }
}