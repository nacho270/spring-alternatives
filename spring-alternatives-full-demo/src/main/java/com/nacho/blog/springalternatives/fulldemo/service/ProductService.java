package com.nacho.blog.springalternatives.fulldemo.service;

import javax.inject.Inject;

public class ProductService {

  @Inject
  public ProductService() {
  }

  //  private ProductRepository productRespository;
//
//  public Product createProduct(final String name, final BigDecimal price) {
//    return productRespository.createProduct(Product.builder() //
//        .name(name) //
//        .price(price) //
//        .build());
//  }
//
//  public List<Product> getProducts() {
//    return productRespository.getProducts();
//  }
//
//  public Product getById(final UUID id) {
//    return productRespository.getById(id);
//  }
}
