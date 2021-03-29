package com.nacho.blog.springalternatives.fulldemo.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

  private UUID id;
  private List<Item> items;
  private BigDecimal total;
  private User user;
}
