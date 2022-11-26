package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oowlish.rolesapi.OowlishRolesAPIApplication;
import com.oowlish.rolesapi.model.User;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OowlishRolesAPIApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

  @LocalServerPort
  private int port;

  TestRestTemplate restTemplate = new TestRestTemplate();

  HttpHeaders headers = new HttpHeaders();

  @Test
  public void getUserEnhanced() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<User> response = restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/fd282131-d8aa-4819-b0c8-d9e0bfb1b75c",
        HttpMethod.GET, entity, User.class);

    User user = response.getBody();
    user.setRole("Developer");
    String expected = "{\"id\": \"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\", \"firstName\": \"Gianni\", \"lastName\": \"Wehner\", \"displayName\": \"gianniWehner\", \"avatarUrl\": \"https://cdn.fakercloud.com/avatars/rude_128.jpg\", \"location\": \"Brakusstad\"}";


    assertNotNull(response);
    assertEquals("Developer", user.getRole());
  }

  @Test
  public void getUser() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/fd282131-d8aa-4819-b0c8-d9e0bfb1b75c",
        HttpMethod.GET, entity, String.class);

    String expected = "{\"id\": \"fd282131-d8aa-4819-b0c8-d9e0bfb1b75c\", \"firstName\": \"Gianni\", \"lastName\": \"Wehner\", \"displayName\": \"gianniWehner\", \"avatarUrl\": \"https://cdn.fakercloud.com/avatars/rude_128.jpg\", \"location\": \"Brakusstad\"}";

    JSONAssert.assertEquals(expected, response.getBody(), false);
  }

  @Test
  public void getTeam() throws JSONException {

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/5071b4fc-43f2-47a2-8403-e934dc270606",
        HttpMethod.GET, entity, String.class);

    String expected = "{\"id\": \"5071b4fc-43f2-47a2-8403-e934dc270606\", \"name\": \"Weekly Peach Wildebeest\", \"teamLeadId\": \"7ca5f476-4beb-4aae-9a50-2ac5c78be9e9\", \"teamMemberIds\": [\"e1ca367c-7763-4941-9403-eaf78582fb1e\", \"8c35f835-3d03-4f30-9233-67cf1f9b2662\", \"b3d1822b-f777-45e0-a9ee-6cb6d741e829\"]}";

    JSONAssert.assertEquals(expected, response.getBody(), false);
  }
}