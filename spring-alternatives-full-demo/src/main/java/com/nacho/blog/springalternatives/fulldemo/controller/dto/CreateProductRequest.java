package com.nacho.blog.springalternatives.fulldemo.controller.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class CreateProductRequest {

  String name;
  BigDecimal price;
}
