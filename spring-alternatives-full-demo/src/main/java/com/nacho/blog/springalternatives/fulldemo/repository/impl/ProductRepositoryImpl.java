package com.nacho.blog.springalternatives.fulldemo.repository.impl;

import com.nacho.blog.springalternatives.fulldemo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

//  private final DSLContext dslContext;
//
//  @Override
//  public Product createProduct(final Product product) {
//    final UUID productId = UUID.randomUUID();
//    dslContext.insertInto(T_PRODUCT, T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE)
//        .values(productId.toString(), product.getName(), product.getPrice()) //
//        .execute();
//    product.setId(productId);
//    return product;
//  }
//
//  @Override
//  public List<Product> getProducts() {
//    return dslContext.select(T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE)//
//        .from(T_PRODUCT) //
//        .fetchInto(Product.class);
//  }
//
//  @Override
//  public Product getById(final UUID id) {
//    return dslContext.select(T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE) //
//        .from(T_PRODUCT) //
//        .where(T_PRODUCT.ID.eq(id.toString())) //
//        .fetchOneInto(Product.class);
//  }
}
