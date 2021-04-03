package com.nacho.blog.springalternatives.fulldemo;

import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.nacho.blog.springalternatives.fulldemo.config.Environment;
import com.nacho.blog.springalternatives.fulldemo.config.LoadDBListener;
import com.nacho.blog.springalternatives.fulldemo.controller.PingController;
import com.nacho.blog.springalternatives.fulldemo.controller.ProductController;
import com.nacho.blog.springalternatives.fulldemo.controller.ShipmentController;
import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateProductRequest;
import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateShipmentRequest;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlMappings {

  private static final int DEFAULT_PORT = 8080;

  private final Environment env;
  private final PingController pingController;
  private final ProductController productController;
  private final ShipmentController shipmentController;
  private final LoadDBListener loadDBListener;

  @Inject
  public UrlMappings(final Environment environment, final PingController pingController, final ProductController productController,
      final ShipmentController shipmentController, final LoadDBListener loadDBListener) {
    this.env = environment;
    this.pingController = pingController;
    this.productController = productController;
    this.shipmentController = shipmentController;
    this.loadDBListener = loadDBListener;
    final var gson = new GsonBuilder().create();
    JavalinJson.setFromJsonMapper(gson::fromJson);
    JavalinJson.setToJsonMapper(gson::toJson);
  }

  public void start() {
    final var app = Javalin.create();

    app.events(e -> e.serverStarted(loadDBListener::insertSampleProducts));

    app.get("/ping", ctx -> ctx.result(pingController.handle()));

    app.post("/product", ctx -> ctx.json(productController.create(ctx.bodyAsClass(CreateProductRequest.class))));
    app.get("/product", ctx -> ctx.json(productController.list()));
    app.get("/product/:id", ctx -> ctx.json(productController.getById(ctx.pathParam("id", UUID.class).get())));

    app.post("/shipment", ctx -> ctx.json(shipmentController.create(ctx.bodyAsClass(CreateShipmentRequest.class))));
    app.get("/shipment/:id", ctx -> ctx.json(shipmentController.getById(ctx.pathParam("id", UUID.class).get())));
    app.get("/shipment/count", ctx -> ctx.result(String.valueOf(shipmentController.count())));
    app.delete("/shipment", ctx -> {
      shipmentController.clearShipments();
      ctx.status(HttpServletResponse.SC_OK);
    });

    final var port = env.getInt("server.port", DEFAULT_PORT);
    app.start(port);
    log.info("Listening on http://localhost:{}/", port);
  }
}
