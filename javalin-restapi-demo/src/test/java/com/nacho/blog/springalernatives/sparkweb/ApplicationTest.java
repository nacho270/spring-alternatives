package com.nacho.blog.springalernatives.sparkweb;

import static kong.unirest.Unirest.get;
import static kong.unirest.Unirest.post;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class ApplicationTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static Application app = new Application();

  @BeforeAll
  public static void beforeSuite() throws Exception {
    app.start();
  }

  @Test
  public void testPing() {
    final HttpResponse<String> response = get("http://localhost:8080/ping").asString();
    assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(response.getBody()).isEqualTo("pong");
  }

  @Test
  public void testGetWithParams() {
    final HttpResponse<String> response = get("http://localhost:8080/hello/user").asString();
    assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(response.getBody()).isEqualTo("Hello user");
  }

  @Test
  public void testGetUserNotFound() {
    final HttpResponse<String> response = get("http://localhost:8080/user/1000").asString();
    assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("User with id [1000] not found");

  }

  @Test
  public void testCreateUser() throws Exception {
    final HttpResponse<?> responseCreate = post("http://localhost:8080/user") //
        .body(new User(1, "user")).asEmpty();
    assertThat(responseCreate.getStatus()).isEqualTo(HttpServletResponse.SC_CREATED);

    final HttpResponse<JsonNode> responseGet = get("http://localhost:8080/user/1").asJson();
    assertThat(responseGet.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(MAPPER.readValue(responseGet.getBody().toString(), User.class).getName()).isEqualTo("user");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSerchUser() throws Exception {
    final HttpResponse<?> responseCreate = post("http://localhost:8080/user") //
        .body(new User(1, "user")).asEmpty();
    assertThat(responseCreate.getStatus()).isEqualTo(HttpServletResponse.SC_CREATED);

    final HttpResponse<JsonNode> responseGet = get("http://localhost:8080/user/search?name=user").asJson();
    assertThat(responseGet.getStatus()).isEqualTo(HttpServletResponse.SC_OK);

    final List<User> users = MAPPER.readValue(responseGet.getBody().toString(), List.class);
    assertThat(users).size().isEqualTo(1);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSerchUserNoResults() throws Exception {
    final HttpResponse<?> responseCreate = post("http://localhost:8080/user") //
        .body(new User(1, "user")).asEmpty();
    assertThat(responseCreate.getStatus()).isEqualTo(HttpServletResponse.SC_CREATED);

    final HttpResponse<JsonNode> responseGet = get("http://localhost:8080/user/search?name=nothing").asJson();
    assertThat(responseGet.getStatus()).isEqualTo(HttpServletResponse.SC_OK);

    final List<User> users = MAPPER.readValue(responseGet.getBody().toString(), List.class);
    assertThat(users).size().isEqualTo(0);
  }

  @Test
  public void testDeleteUser() throws Exception {
    final HttpResponse<?> responseCreate = post("http://localhost:8080/user") //
        .body(new User(2, "user")).asEmpty();
    assertThat(responseCreate.getStatus()).isEqualTo(HttpServletResponse.SC_CREATED);

    final HttpResponse<JsonNode> responseGet = get("http://localhost:8080/user/2").asJson();
    assertThat(responseGet.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(MAPPER.readValue(responseGet.getBody().toString(), User.class).getName()).isEqualTo("user");

    final HttpResponse<?> responseDelete = Unirest.delete("http://localhost:8080/user/2").asEmpty();
    assertThat(responseDelete.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
  }

  @Test
  public void testBadRequest() {
    final HttpResponse<?> responseCreate = post("http://localhost:8080/user") //
        .body("bla").asEmpty();
    assertThat(responseCreate.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
  }

  @Test
  public void testException() {
    final HttpResponse<?> responseDelete = Unirest.delete("http://localhost:8080/user/bla").asEmpty();
    assertThat(responseDelete.getStatus()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }
}
