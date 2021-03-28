package com.nacho.blog.springalternatives.jooqdemo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.nacho.blog.springalternatives.jooqdemo.model.Product;
import com.nacho.blog.springalternatives.jooqdemo.service.ItemRequest;
import com.nacho.blog.springalternatives.jooqdemo.service.ProductService;
import com.nacho.blog.springalternatives.jooqdemo.service.ShipmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

  final String userName = "root";
  final String password = "root";
  final String url = "jdbc:mysql://localhost:3306/jooq";

  private ProductService productService;
  private ShipmentService shipmentService;

  public Application() {

    try (Connection conn = DriverManager.getConnection(url, userName, password)) {
      final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
      productService = new ProductService(context);
      shipmentService = new ShipmentService(context, productService);
      final List<Product> products = createProducts();
      log.info("Created shipment {}", shipmentService.createShipment( //
          products.stream() //
              .map(p -> ItemRequest.builder().product(p.getId()).quantity(1).build()) //
              .collect(Collectors.toList())));
      log.info("Shipment count: {}", shipmentService.getShipmentCount());
      log.info("Fetching all shipments...");
      shipmentService.getAll().forEach(ship -> log.info("{}", ship));
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    new Application();
  }

  private List<Product> createProducts() {
    return List.of(//
        productService.createProduct(//
            Product.builder() //
                .name("monitor") //
                .price(BigDecimal.valueOf(500)) //
                .build()), //
        productService.createProduct(//
            Product.builder() //
                .name("macbook pro") //
                .price(BigDecimal.valueOf(3000)) //
                .build()));

  }
}
