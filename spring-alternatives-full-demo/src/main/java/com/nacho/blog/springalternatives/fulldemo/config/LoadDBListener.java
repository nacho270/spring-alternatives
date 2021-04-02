package com.nacho.blog.springalternatives.fulldemo.config;

import com.nacho.blog.springalternatives.fulldemo.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Slf4j
@Singleton
public class LoadDBListener {

  private final ProductService productService;

  @Inject
  public LoadDBListener(ProductService productService) {
    this.productService = productService;
  }

  public void insertSampleProducts() {
    log.info("inserting sample data");
    log.info("Created product: {}", productService.createProduct("book", BigDecimal.valueOf(10.5)));
    log.info("Created product: {}", productService.createProduct("macbook pro", BigDecimal.valueOf(3000)));
    log.info("Created product: {}", productService.createProduct("monitor", BigDecimal.valueOf(500)));
    log.info("Created product: {}", productService.createProduct("mouse", BigDecimal.valueOf(18.9)));
    log.info("finished inserting sample data");
  }
}
