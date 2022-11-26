package com.oowlish.rolesapi.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


import com.oowlish.rolesapi.model.User;
import com.oowlish.rolesapi.service.impl.UserServiceImpl;
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
class UserServiceTest {

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private RolService rolService;
  @InjectMocks
  private UserServiceImpl classUnderTest;


  @Test
  public void getUser() {

    String userUUID = UUID.randomUUID().toString();
    String firstName = "Gianni";
    String lastName = "Wehner";
    String displayName = "gianniWehner";
    String avatarUrl = "https://cdn.fakercloud.com/avatars/rude_128.jpg";
    String location = "Brakusstad";

    JSONObject jsonObject = new JSONObject();
    jsonObject.appendField("id", userUUID);
    jsonObject.appendField("firstName", firstName);
    jsonObject.appendField("lastName", lastName);
    jsonObject.appendField("displayName", displayName);
    jsonObject.appendField("avatarUrl", avatarUrl);
    jsonObject.appendField("location", location);

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    when(restTemplate.exchange(
        String.format("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/%s", userUUID),
        HttpMethod.GET, entity, JSONObject.class)).thenReturn(new ResponseEntity<>(jsonObject, HttpStatus.OK));

    JSONObject userRetrieved = classUnderTest.getUser(userUUID);

    assertNotNull(userRetrieved);
    assertNotNull(userRetrieved.get("firstName"));
    assertNotNull(userRetrieved.get("lastName"));
    assertNotNull(userRetrieved.get("avatarUrl"));
    assertNotNull(userRetrieved.get("displayName"));
    assertNotNull(userRetrieved.get("location"));
    assertNotNull(userRetrieved.get("id"));

  }

  @Test
  public void getUsers(){
    UUID userUUID = UUID.randomUUID();
    String firstName = "Gianni";
    String lastName = "Wehner";
    String displayName = "gianniWehner";
    String avatarUrl = "https://cdn.fakercloud.com/avatars/rude_128.jpg";
    String location = "Brakusstad";

    List<User> users = List.of(User.builder().id(userUUID).firstName(firstName).lastName(lastName).displayName(displayName).avatarUrl(avatarUrl).location(location).build(),
        User.builder().id(userUUID).firstName(firstName).lastName(lastName).displayName(displayName).avatarUrl(avatarUrl).location(location).build());

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

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    when(restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/",
        HttpMethod.GET, entity, JSONObject[].class)).thenReturn(new ResponseEntity<>(jsonObjects, HttpStatus.OK));

    JSONObject[] usersRetrieved = classUnderTest.getUsers();

    assertNotNull(usersRetrieved);
    assertNotNull(usersRetrieved[0].get("firstName"));
    assertNotNull(usersRetrieved[0].get("lastName"));
    assertNotNull(usersRetrieved[0].get("avatarUrl"));
    assertNotNull(usersRetrieved[0].get("displayName"));
    assertNotNull(usersRetrieved[0].get("location"));
    assertNotNull(usersRetrieved[0].get("id"));

  }

}