package com.nacho.blog.springalternatives.fulldemo.service;

import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateShipmentRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Item;
import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ShipmentService {

  private final ShipmentRepository shipmentRepository;

  private final ProductService productService;

  @Inject
  public ShipmentService(ShipmentRepository shipmentRepository, ProductService productService) {
    this.shipmentRepository = shipmentRepository;
    this.productService = productService;
  }

  public Shipment getById(final UUID id) {
    return shipmentRepository.getById(id);
  }

  public Shipment createShipment(final List<CreateShipmentRequest.ItemRequest> requestItems) {
    Shipment shipment = mapShipment(requestItems);

    shipment = shipmentRepository.insertShipment(shipment);

    shipmentRepository.insertShipmentItems(shipment);

    return shipment;
  }

  public Integer getShipmentCount() {
    return shipmentRepository.getShipmentCount();
  }

  public void clearShipments(){
    shipmentRepository.clearShipments();
  }

  private Shipment mapShipment(final List<CreateShipmentRequest.ItemRequest> requestItems) {
    var items = requestItems.stream() //
            .map(this::mapItem) //
            .collect(Collectors.toList());
    return Shipment.builder()
            .items(items)
            .total(items.stream()//
                    .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity().longValue()))) //
                    .reduce(BigDecimal.ZERO, BigDecimal::add))
            .build();
  }

  private Item mapItem(final CreateShipmentRequest.ItemRequest ir) {
    return Item.builder() //
            .id(UUID.randomUUID()) //
            .product(productService.getById(ir.getProduct())) //
            .quantity(ir.getQuantity()) //
            .build();
  }

}
