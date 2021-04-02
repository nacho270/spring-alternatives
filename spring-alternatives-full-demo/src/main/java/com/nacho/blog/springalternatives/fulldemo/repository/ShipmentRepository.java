package com.nacho.blog.springalternatives.fulldemo.repository;

import com.nacho.blog.springalternatives.fulldemo.model.Shipment;

import java.util.UUID;

public interface ShipmentRepository {

  Shipment getById(UUID id);

  Shipment insertShipment(Shipment shipment);

  void insertShipmentItems(Shipment shipment);

  Integer getShipmentCount();

  void clearShipments();

}
