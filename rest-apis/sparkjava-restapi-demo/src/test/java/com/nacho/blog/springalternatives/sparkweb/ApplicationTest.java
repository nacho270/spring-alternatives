package com.nacho.blog.springalternatives.sparkweb;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.nacho.blog.springalternatives.sparkweb.Application;
import com.nacho.blog.springalternatives.sparkweb.User;

import io.restassured.response.Response;

public class ApplicationTest {

  private static final Gson GSON = new Gson();

  @BeforeAll
  public static void beforeSuite() throws Exception {
    new Application().start();
  }

  @Test
  public void testPing() {
    final Response response = get("http://localhost:8080/ping");
    assertThat(response.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(response.getBody().print()).isEqualTo("pong");
  }

  @Test
  public void testGetWithParams() {
    final Response response = get("http://localhost:8080/hello/user");
    assertThat(response.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(response.getBody().print()).isEqualTo("Hello user");
  }

  @Test
  public void testGetUserNotFound() {
    final String response = with() //
        .when()//
        .get("http://localhost:8080/user/1000") //
        .then() //
        .statusCode(404).extract().response().asString();
    assertThat(response).isEqualTo("User with id [1000] not found");

  }

  @Test
  public void testCreateUser() {
    with() //
        .body(new User(1, "user")) //
        .when()//
        .post("http://localhost:8080/user") //
        .then() //
        .statusCode(201);

    final Response responseGet = get("http://localhost:8080/user/1");
    assertThat(responseGet.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(GSON.fromJson(responseGet.body().print(), User.class).getName()).isEqualTo("user");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSerchUser() {
    with() //
        .body(new User(1, "user")) //
        .when()//
        .post("http://localhost:8080/user") //
        .then() //
        .statusCode(201);

    final Response responseGet = get("http://localhost:8080/user/search?name=user");
    assertThat(responseGet.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);

    final List<User> users = GSON.fromJson(responseGet.body().print(), List.class);
    assertThat(users).size().isEqualTo(1);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSerchUserNoResults() {
    with() //
        .body(new User(1, "user")) //
        .when()//
        .post("http://localhost:8080/user") //
        .then() //
        .statusCode(201);

    final Response responseGet = get("http://localhost:8080/user/search?name=nothing");
    assertThat(responseGet.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);

    final List<User> users = GSON.fromJson(responseGet.body().print(), List.class);
    assertThat(users).size().isEqualTo(0);
  }

  @Test
  public void testDeleteUser() {
    with() //
        .body(new User(2, "user")) //
        .when()//
        .post("http://localhost:8080/user") //
        .then() //
        .statusCode(201);

    final Response responseGet = get("http://localhost:8080/user/2");
    assertThat(responseGet.getStatusCode()).isEqualTo(HttpServletResponse.SC_OK);
    assertThat(GSON.fromJson(responseGet.body().print(), User.class).getName()).isEqualTo("user");

    with() //
        .when()//
        .delete("http://localhost:8080/user/2") //
        .then() //
        .statusCode(200);
  }

  @Test
  public void testBadRequest() {
    with() //
        .body("bla") //
        .when()//
        .post("http://localhost:8080/user") //
        .then() //
        .statusCode(400);
  }

  @Test
  public void testException() {
    with() //
        .when()//
        .delete("http://localhost:8080/user/bla") //
        .then() //
        .statusCode(500);
  }
}
