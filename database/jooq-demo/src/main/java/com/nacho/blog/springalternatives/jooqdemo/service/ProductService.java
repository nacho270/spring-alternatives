package com.nacho.blog.springalternatives.jooqdemo.service;

import static com.nacho.blog.springalternatives.jooqdemo.gen.Tables.T_PRODUCT;

import java.util.UUID;

import org.jooq.DSLContext;

import com.nacho.blog.springalternatives.jooqdemo.model.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService {

  private final DSLContext context;

  public Product createProduct(final Product product) {
    final UUID productId = UUID.randomUUID();
    context.insertInto(T_PRODUCT, T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE)
        .values(productId.toString(), product.getName(), product.getPrice()) //
        .execute();
    product.setId(productId);
    return product;
  }

  public Product getById(final UUID id) {
    return context.select(T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE) //
        .from(T_PRODUCT) //
        .where(T_PRODUCT.ID.eq(id.toString())) //
        .fetchOneInto(Product.class);
  }
}
