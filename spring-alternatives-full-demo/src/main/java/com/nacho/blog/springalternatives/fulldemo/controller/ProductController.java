package com.nacho.blog.springalternatives.fulldemo.controller;

import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateProductRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Product;
import com.nacho.blog.springalternatives.fulldemo.service.ProductService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class ProductController {

  private final ProductService productService;

  @Inject
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  public List<Product> list() {
    return productService.getProducts();
  }

  public Product create(CreateProductRequest createProductRequest) {
    return productService.createProduct(createProductRequest.getName(), createProductRequest.getPrice());
  }

  public Product getById(UUID id) {
    return productService.getById(id);
  }
}
