package com.nacho.blog.springalternatives.fulldemo.repository;

import com.nacho.blog.springalternatives.fulldemo.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

  Product createProduct(Product product);

  List<Product> getProducts();

  Product getById(UUID id);
}
