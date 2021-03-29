package com.nacho.blog.springalternatives.fulldemo.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  private UUID id;
  private Product product;
  private Integer quantity;
}
