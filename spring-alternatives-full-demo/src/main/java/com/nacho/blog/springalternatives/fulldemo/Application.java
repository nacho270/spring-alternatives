package com.nacho.blog.springalternatives.fulldemo;

import javax.inject.Inject;

import com.nacho.blog.springalternatives.fulldemo.config.Environment;
import com.nacho.blog.springalternatives.fulldemo.controller.PingController;

import lombok.extern.slf4j.Slf4j;
import spark.Spark;

@Slf4j
public class Application {

  private static final int DEFAULT_PORT = 8080;

  private Environment env;

  @Inject
  public Application(final Environment environment) {
    this.env = environment;
  }

  public void start() {
    Spark.port(env.getInt("server.port", DEFAULT_PORT));
    Spark.get("/ping", PingController::handle);
    log.info("Listening on http://localhost:8080/");
  }
}
