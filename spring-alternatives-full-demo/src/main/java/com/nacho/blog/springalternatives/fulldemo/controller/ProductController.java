package com.nacho.blog.springalternatives.fulldemo.controller;

import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateProductRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Product;
import com.nacho.blog.springalternatives.fulldemo.service.ProductService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductController {

  private final ProductService productService;

  @Inject
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  public List<Product> list() {
    return null;
  }

  public String create(CreateProductRequest createProductRequest) {
//final CreateProductRequest createProductRequest
//        productService.createProduct(createProductRequest.getName(), createProductRequest.getPrice());
    return "ok";
  }

  public Product getById(Integer id) {
    return Product.builder().id(UUID.randomUUID()).name("name").price(BigDecimal.TEN).build();
  }
}
