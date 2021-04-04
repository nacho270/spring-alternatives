package com.nacho.blog.springalternatives.javalin;

import static java.lang.System.currentTimeMillis;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

  private static final Map<Integer, User> USERS = new HashMap<>();

  public void start() {
    final Javalin app = Javalin.create();
    final var objectMapper = new ObjectMapper();
    JavalinJackson.configure(objectMapper);

    app.before(ctx -> {
      ctx.req.setAttribute("request-start", currentTimeMillis());
      log.info("Before every request! Potentially could validate Auth token here too");
    });

    app.before(ctx -> {
      final long start = (long) ctx.req.getAttribute("request-start");
      log.info("After every request! Add headers, report times...This request took {}ms", currentTimeMillis() - start);
    });

    app.exception(NumberFormatException.class, (e, ctx) -> {
      log.error("Exception thrown!! {}", e);
      ctx.res.setContentType("text/plain");
      ctx.res.setStatus(500);
      ctx.result("Error");
    });

    app.exception(NotFoundException.class, (e, ctx) -> {
      ctx.res.setContentType("text/plain");
      ctx.res.setStatus(404);
      ctx.result(e.getMessage());
    });

    app.get("/ping", ctx -> ctx.result("pong"));

    app.get("/hello/:name", ctx -> ctx.result("Hello " + ctx.pathParam("name")));

    app.post("/user", (ctx) -> {
      try {
        final User user = ctx.bodyAsClass(User.class);
        USERS.put(user.getId(), user);
        ctx.res.setStatus(201);
      } catch (final Exception e) {
        ctx.res.setStatus(400);
      }
    });

    // the order is important between the search and the /:id
    // if the search were below, the framework will match the /:id using `search` as user id.
    app.get("/user/search", (ctx) -> {
      final String nameToSearch = ctx.queryParam("name");
      ctx.json(USERS.values() //
          .stream() //
          .filter(u -> u.getName().contains(nameToSearch)) //
          .collect(Collectors.toList()));
    });

    app.get("/user/:id", (ctx) -> {
      final Integer id = Integer.valueOf(ctx.pathParam("id"));
      final User maybeUser = USERS.get(id);
      ctx.json(Optional.ofNullable(maybeUser) //
          .orElseThrow(() -> new NotFoundException(id)));
    });

    app.delete("/user/:id", (ctx) -> USERS.remove(Integer.valueOf(ctx.pathParam("id"))));

    app.start(8080);
    log.info("Listening on http://localhost:8080/");
  }

  private static class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4113048522411890988L;

    public NotFoundException(final int id) {
      super("User with id [" + id + "] not found");
    }
  }
}
