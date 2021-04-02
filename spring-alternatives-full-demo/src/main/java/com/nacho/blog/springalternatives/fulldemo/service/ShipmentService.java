package com.nacho.blog.springalternatives.fulldemo.service;

import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateShipmentRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Item;
import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Slf4j
@Singleton
public class ShipmentService {

  private final ShipmentRepository shipmentRepository;
  private final ProductService productService;
  private final UserService userService;
  private final TransactionManager transactionManager;

  @Inject
  public ShipmentService(ShipmentRepository shipmentRepository, ProductService productService,
                         UserService userService, TransactionManager transactionManager) {
    this.shipmentRepository = shipmentRepository;
    this.productService = productService;
    this.userService = userService;
    this.transactionManager = transactionManager;
  }

  public Shipment getById(final UUID id) {
    return shipmentRepository.getById(id);
  }

  public Shipment createShipment(final CreateShipmentRequest createShipmentRequest) {
    return transactionManager.doInTransaction(configuration -> {
      try {
        Shipment shipment = mapShipment(createShipmentRequest);
        log.info("Saving shipment");
        shipment = shipmentRepository.insertShipment(shipment);
        log.info("Saving items");
        shipmentRepository.insertShipmentItems(shipment);
        log.info("Created shipment: {}", shipment);
        return shipment;
      } catch (Exception e) {
        log.error("Error saving shipment: {}", e.getMessage());
        throw e;
      }
    });
  }

  public Integer getShipmentCount() {
    return shipmentRepository.getShipmentCount();
  }

  public void clearShipments() {
    shipmentRepository.clearShipments();
  }

  private Shipment mapShipment(final CreateShipmentRequest createShipmentRequest) {
    var items = createShipmentRequest.getItems().stream() //
            .map(this::mapItem) //
            .collect(toList());
    return Shipment.builder()
            .items(items) //
            .user(userService.getById(createShipmentRequest.getUserId()))
            .total(items.stream() //
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
