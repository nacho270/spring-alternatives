package com.nacho.blog.springalternatives.jooqdemo.service;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRequest {
  private UUID product;
  private Integer quantity;
}