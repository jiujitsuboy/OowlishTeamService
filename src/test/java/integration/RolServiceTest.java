package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.oowlish.rolesapi.OowlishRolesAPIApplication;
import com.oowlish.rolesapi.TestConstants;
import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.model.Rol;
import com.oowlish.rolesapi.model.UserRol;
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
class RolServiceTest {

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


    ResponseEntity<Rol[]> response = restTemplate.exchange(
        createURLWithPort("/api/v1/rol/"),
        HttpMethod.GET, entity, Rol[].class);

    var user = response.getBody();

    Assertions.assertTrue(user.length > 0);
  }

  @Test
  public void getRolDeveloper() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Rol> response = restTemplate.exchange(
        createURLWithPort("/api/v1/rol/1"),
        HttpMethod.GET, entity, Rol.class);

    var rol = response.getBody();

    assertEquals("Developer", rol.getName());
  }

  @Test
  public void getRolUnknown() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<Rol> response = restTemplate.exchange(
        createURLWithPort("/api/v1/rol/500"),
        HttpMethod.GET, entity, Rol.class);

    var rol = response.getBody();

    assertNull(rol.getName());
  }

  @Test
  public void createRol() throws JSONException {

    headers.add("Content-Type", "application/json");
    Rol rol = Rol.builder().name("QA").build();

    HttpEntity<Rol> entity = new HttpEntity<>(rol, headers);

    ResponseEntity<Rol> response = restTemplate.exchange(
        createURLWithPort("/api/v1/rol/"),
        HttpMethod.POST, entity, Rol.class);

    var rolCreated = response.getBody();

    assertNotNull(rolCreated.getId());
    assertEquals(rol.getName(), rolCreated.getName());
  }

  @Test
  public void assignRol() {

    String idUser = "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e";
    String idTeam = "7676a4bf-adfe-415c-941b-1739af07039b";

    headers.add("Content-Type", "application/json");
    UserRol userRol = UserRol.builder().idUser(idUser).idTeam(idTeam).rol(Rol.builder().id(4L).name("QA").build()).build();

    HttpEntity<UserRol> entity = new HttpEntity<>(userRol, headers);

    ResponseEntity<UserRol> response = restTemplate.exchange(
        createURLWithPort("/api/v1/rol/"),
        HttpMethod.PATCH, entity, UserRol.class);

    var userRolCreated = response.getBody();

    assertNotNull(userRolCreated.getId());
    assertEquals(userRol.getRol().getName(), userRolCreated.getRol().getName());
  }


  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }


}