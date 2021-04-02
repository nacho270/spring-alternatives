package com.nacho.blog.springalternatives.fulldemo.repository;

import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import org.jooq.Configuration;

import java.util.UUID;

public interface ShipmentRepository {

  Shipment getById(UUID id);

  Shipment insertShipment(Configuration configuration, Shipment shipment);

  void insertShipmentItems(Configuration configuration, Shipment shipment);

  Integer getShipmentCount();

  void clearShipments();

}
