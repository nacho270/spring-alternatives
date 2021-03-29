package com.nacho.blog.springalternatives.fulldemo;

import com.nacho.blog.springalternatives.fulldemo.config.Environment;
import com.nacho.blog.springalternatives.fulldemo.controller.PingController;
import com.nacho.blog.springalternatives.fulldemo.controller.ProductController;
import lombok.extern.slf4j.Slf4j;
import spark.Spark;

import javax.inject.Inject;

import static spark.Spark.*;

@Slf4j
public class UrlMappings {

  private static final int DEFAULT_PORT = 8080;

  private final Environment env;
  private final PingController pingController;
  private final ProductController productController;

  @Inject
  public UrlMappings(final Environment environment, final PingController pingController, final ProductController productController) {
    this.env = environment;
    this.pingController = pingController;
    this.productController = productController;
  }

  public void start() {
    Integer port = env.getInt("server.port", DEFAULT_PORT);
    Spark.port(port);

    get("/ping", pingController::handle);

    path("/product", () -> {
      post("/", productController::create);
      get("/", productController::list);
    });

    path("/shipment", () -> {
      post("/", (req, res) -> "");
      get("/:id", (req, res) -> "");
    });

    log.info("Listening on http://localhost:{}/", port);
  }
}
