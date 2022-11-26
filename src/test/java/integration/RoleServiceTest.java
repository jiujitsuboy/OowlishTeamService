package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.oowlish.rolesapi.OowlishRolesAPIApplication;
import com.oowlish.rolesapi.TestConstants;
import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.model.Role;
import com.oowlish.rolesapi.model.UserRole;
import com.oowlish.rolesapi.security.JwtManager;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OowlishRolesAPIApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "../import.sql")
class RoleServiceTest {

  @LocalServerPort
  private int port;

  @Autowired
  private JwtManager tokenManager;

  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();
  private String token;


  @BeforeEach
  public void setup(){
    restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    token = TestConstants.getToken(tokenManager, RoleEnum.USER.name());
    headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+ token);
  }



  @Test
  public void getRoles() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);


    ResponseEntity<Role[]> response = restTemplate.exchange(
        createURLWithPort("/api/v1/role/"),
        HttpMethod.GET, entity, Role[].class);

    var user = response.getBody();

    Assertions.assertTrue(user.length > 0);
  }

  @Test
  public void getRolDeveloper() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Role> response = restTemplate.exchange(
        createURLWithPort("/api/v1/role/1"),
        HttpMethod.GET, entity, Role.class);

    var rol = response.getBody();

    assertEquals("Developer", rol.getName());
  }

  @Test
  public void getRolUnknown() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Role> response = restTemplate.exchange(
        createURLWithPort("/api/v1/role/500"),
        HttpMethod.GET, entity, Role.class);

    var rol = response.getBody();

    assertNull(rol.getName());
  }

  @Test
  public void createRol() throws JSONException {

    headers.add("Content-Type", "application/json");
    Role rol = Role.builder().name("QA").build();

    HttpEntity<Role> entity = new HttpEntity<>(rol, headers);

    ResponseEntity<Role> response = restTemplate.exchange(
        createURLWithPort("/api/v1/role/"),
        HttpMethod.POST, entity, Role.class);

    var rolCreated = response.getBody();

    assertNotNull(rolCreated.getId());
    assertEquals(rol.getName(), rolCreated.getName());
  }

  @Test
  public void assignRol() {

    String idUser = "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e";
    String idTeam = "7676a4bf-adfe-415c-941b-1739af07039b";

    headers.add("Content-Type", "application/json");
    UserRole userRol = UserRole.builder().idUser(idUser).idTeam(idTeam).rol(
        Role.builder().id(4L).name("QA").build()).build();

    HttpEntity<UserRole> entity = new HttpEntity<>(userRol, headers);

    ResponseEntity<UserRole> response = restTemplate.exchange(
        createURLWithPort("/api/v1/role/"),
        HttpMethod.PATCH, entity, UserRole.class);

    var userRolCreated = response.getBody();

    assertNotNull(userRolCreated.getId());
    assertEquals(userRol.getRol().getName(), userRolCreated.getRol().getName());
  }


  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }


}