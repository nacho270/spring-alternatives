package com.nacho.blog.springalternatives.fulldemo.controller;

import javax.inject.Inject;

public class ShipmentController {

  @Inject
  public ShipmentController() {
  }

  //    @Autowired
//    private ShipmentService shipmentService;
//
//    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<Shipment> getById(@PathVariable("id") final UUID id) {
//        return ResponseEntity.ok(shipmentService.getById(id));
//    }
//
//    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<UUID> create(@RequestBody final CreateShipmentRequest createShipmentRequest) {
//        return ResponseEntity.ok(shipmentService.createShipment(createShipmentRequest.getItems()));
//    }
}
