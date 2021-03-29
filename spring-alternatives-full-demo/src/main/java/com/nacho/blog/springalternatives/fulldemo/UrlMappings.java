package com.nacho.blog.springalternatives.fulldemo;

import com.google.gson.GsonBuilder;
import com.nacho.blog.springalternatives.fulldemo.config.Environment;
import com.nacho.blog.springalternatives.fulldemo.controller.PingController;
import com.nacho.blog.springalternatives.fulldemo.controller.ProductController;
import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateProductRequest;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

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

    var gson = new GsonBuilder().create();
    JavalinJson.setFromJsonMapper(gson::fromJson);
    JavalinJson.setToJsonMapper(gson::toJson);
  }

  public void start() {
    final var app = Javalin.create();
    app.get("/ping", ctx -> ctx.result(pingController.handle()));

    app.post("/product", ctx -> ctx.json(productController.create(ctx.bodyAsClass(CreateProductRequest.class))));
    app.get("/product", ctx -> ctx.json(productController.list()));
    app.get("/product/:id", ctx->ctx.json(productController.getById(ctx.pathParam("id", Integer.class).get())));

    app.post("/shipment", ctx -> ctx.status(HttpServletResponse.SC_FORBIDDEN));
    app.get("/shipment/:id", ctx -> ctx.status(HttpServletResponse.SC_FORBIDDEN));

    var port = env.getInt("server.port", DEFAULT_PORT);
    app.start(port);
    log.info("Listening on http://localhost:{}/", port);
  }
}
