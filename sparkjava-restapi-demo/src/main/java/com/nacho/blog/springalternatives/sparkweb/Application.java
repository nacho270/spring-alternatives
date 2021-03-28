package com.nacho.blog.springalternatives.sparkweb;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;
import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import spark.Spark;

@Slf4j
public class Application {

  private static final Gson GSON = new Gson();

  private static final Map<Integer, User> USERS = new HashMap<>();

  public void start() {
    Spark.port(8080);

    before((req, resp) -> {
      req.attribute("request-start", currentTimeMillis());
      log.info("Before every request! Potentially could validate Auth token here too");
    });

    after((req, resp) -> {
      final long start = (long) req.attribute("request-start");
      log.info("After every request! Add headers, report times...This request took {}ms", currentTimeMillis() - start);
    });

    exception(NumberFormatException.class, (e, req, resp) -> {
      log.error("Exception thrown!! {}", e);
      resp.type("text/plain");
      resp.status(500);
      resp.body("Error");
    });

    exception(NotFoundException.class, (e, req, resp) -> {
      resp.status(404);
      resp.type("text/plain");
      resp.body(e.getMessage());
    });

    get("/ping", (req, res) -> "pong");

    get("/hello/:name", (req, res) -> "Hello " + req.params("name"));

    path("/user", () -> {

      post("", (req, res) -> {
        try {
          final User user = GSON.fromJson(req.body(), User.class);
          USERS.put(user.getId(), user);
          res.status(201);
          return "";
        } catch (final Exception e) {
          res.status(400);
          return "bad-request";
        }
      });

      // the order is important between the search and the /:id
      // if the search were below, the framework will match the /:id using `search` as user id.
      get("/search", (req, resp) -> {
        final String nameToSearch = req.queryParams("name");
        return USERS.values() //
            .stream() //
            .filter(u -> u.getName().contains(nameToSearch)) //
            .collect(toList());
      }, GSON::toJson);

      get("/:id", (req, res) -> {
        final Integer id = Integer.valueOf(req.params("id"));
        final User maybeUser = USERS.get(id);
        return Optional.ofNullable(maybeUser) //
            .orElseThrow(() -> new NotFoundException(id));
      }, GSON::toJson);

      delete("/:id", (req, resp) -> {
        USERS.remove(Integer.valueOf(req.params("id")));
        return "ok";
      });
    });

    log.info("Listening on http://localhost:8080/");
  }

  private static class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4113048522411890988L;

    public NotFoundException(final int id) {
      super("User with id [" + id + "] not found");
    }
  }
}
