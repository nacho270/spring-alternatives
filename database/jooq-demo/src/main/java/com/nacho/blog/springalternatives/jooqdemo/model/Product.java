package com.nacho.blog.springalternatives.jooqdemo.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private UUID id;
  private String name;
  private BigDecimal price;
}
