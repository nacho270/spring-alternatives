package com.nacho.blog.springalternatives.fulldemo.controller;

import com.nacho.blog.springalternatives.fulldemo.model.Product;
import com.nacho.blog.springalternatives.fulldemo.service.ProductService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

public class ProductController {

  private final ProductService productService;

  @Inject
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

    public List<Product> list(final Request request, final Response response) {
        return null;
    }

    public String create(final Request request, final Response response) {
//final CreateProductRequest createProductRequest
//        productService.createProduct(createProductRequest.getName(), createProductRequest.getPrice());
        return "ok";
    }
}
