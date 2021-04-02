package com.nacho.blog.springalternatives.fulldemo.service;

import com.nacho.blog.springalternatives.fulldemo.model.Product;
import com.nacho.blog.springalternatives.fulldemo.repository.ProductRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Singleton
public class ProductService {

  private final ProductRepository productRepository;

  @Inject
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product createProduct(final String name, final BigDecimal price) {
    return productRepository.createProduct(Product.builder() //
        .name(name) //
        .price(price) //
        .build());
  }

  public List<Product> getProducts() {
    return productRepository.getProducts();
  }

  public Product getById(final UUID id) {
    return productRepository.getById(id);
  }
}
