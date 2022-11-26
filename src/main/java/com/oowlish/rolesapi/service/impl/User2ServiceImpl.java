package com.oowlish.rolesapi.service.impl;

import com.oowlish.rolesapi.service.RolService;
import com.oowlish.rolesapi.service.User2Service;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class User2ServiceImpl implements User2Service {

  private RestTemplate restTemplate;
  private RolService rolService;


  public User2ServiceImpl(RestTemplate restTemplate, RolService rolService) {
    this.restTemplate = restTemplate;
    this.rolService=rolService;
  }

  @Override
  public JSONObject getUser(String userId) {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<JSONObject> response = restTemplate.exchange(
        String.format("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/%s", userId),
        HttpMethod.GET, entity, JSONObject.class);

    return response.getBody();
  }

  @Override
  public JSONObject[] getUsers() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<JSONObject[]> response = restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/",
        HttpMethod.GET, entity, JSONObject[].class);

    return response.getBody();
  }
}
