package com.nacho.blog.springalternatives.fulldemo.controller;

import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateShipmentRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import com.nacho.blog.springalternatives.fulldemo.service.ShipmentService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ShipmentController {

  private final ShipmentService shipmentService;

  @Inject
  public ShipmentController(ShipmentService shipmentService) {
    this.shipmentService = shipmentService;
  }

  public Shipment getById(final UUID id) {
    return shipmentService.getById(id);
  }

  public Shipment create(final CreateShipmentRequest createShipmentRequest) {
    return shipmentService.createShipment(createShipmentRequest.getItems());
  }

  public Integer count() {
    return shipmentService.getShipmentCount();
  }

  public void clearShipments() {
    shipmentService.clearShipments();
  }
}
