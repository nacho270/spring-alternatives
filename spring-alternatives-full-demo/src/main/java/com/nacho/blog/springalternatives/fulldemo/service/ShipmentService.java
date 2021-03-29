package com.nacho.blog.springalternatives.fulldemo.service;

public class ShipmentService {

//  private ShipmentRepository shipmentRepository;
//
//  private ProductService productService;
//
//  public Shipment getById(final UUID id) {
//    return shipmentRepository.getById(id);
//  }
//
//  public UUID createShipment(final List<ItemRequest> requestItems) {
//    Shipment shipment = mapShipment(requestItems);
//
//    shipment = shipmentRepository.insertShipment(shipment);
//
//    shipmentRepository.insertShipmentItems(shipment);
//
//    return shipment.getId();
//  }
//
//  public Integer getShipmentCount() {
//    return shipmentRepository.getShipmentCount();
//  }
//
//  private Shipment mapShipment(final List<ItemRequest> requestItems) {
//    final Shipment shipment = new Shipment();
//    final List<Item> items = requestItems.stream() //
//        .map(this::mapItem) //
//        .collect(Collectors.toList());
//    shipment.setItems(items);
//    shipment.setTotal(items.stream()//
//        .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity().longValue()))) //
//        .reduce(BigDecimal.ZERO, BigDecimal::add));
//    return shipment;
//  }
//
//  private Item mapItem(final ItemRequest ir) {
//    final Product product = productService.getById(ir.getProduct());
//    final Item item = Item.builder() //
//        .id(UUID.randomUUID()) //
//        .product(product) //
//        .quantity(ir.getQuantity()) //
//        .build();
//    return item;
//  }

}
