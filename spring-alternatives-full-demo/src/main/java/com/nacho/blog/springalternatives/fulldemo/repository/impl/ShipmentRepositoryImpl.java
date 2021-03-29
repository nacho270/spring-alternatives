package com.nacho.blog.springalternatives.fulldemo.repository.impl;

import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository {
//
//  private DSLContext dslContext;
//
//  @Override
//  public Shipment getById(final UUID id) {
//    // Make use of the JSON binding for mapping. Awesome feature!
//    // See here: https://blog.jooq.org/2020/10/09/nesting-collections-with-jooq-3-14s-sql-xml-or-sql-json-support/
//    // This delegates the mapping to eithergson, Jackson, JAXB... whichever is present in the classpath.
//    return dslContext.select( //
//        jsonObject( //
//            key("id").value(T_SHIPMENT.ID), //
//            key("total").value(T_SHIPMENT.TOTAL), //
//            key("items").value( //
//                jsonArrayAgg( //
//                    jsonObject( //
//                        key("id").value(T_ITEM.ID), //
//                        key("quantity").value(T_ITEM.QUANTITY), //
//                        key("product").value(jsonObject( //
//                            key("id").value(T_PRODUCT.ID), //
//                            key("name").value(T_PRODUCT.NAME), //
//                            key("price").value(T_PRODUCT.PRICE) //
//                        )) //
//                    ) //
//                )) //
//        )) //
//        .from(T_SHIPMENT) //
//        .innerJoin(T_ITEM).on(T_ITEM.F_SHIPMENT_ID.eq(T_SHIPMENT.ID)) //
//        .innerJoin(T_PRODUCT).on(T_ITEM.F_PRODUCT_ID.eq(T_PRODUCT.ID)) //
//        .where(T_SHIPMENT.ID.equal(id.toString())) //
//        .groupBy(T_SHIPMENT.ID) //
//        .fetchOneInto(Shipment.class);
//  }
//
//  @Override
//  public Shipment insertShipment(final Shipment shipment) {
//    final UUID shipmentId = UUID.randomUUID();
//    dslContext.insertInto(T_SHIPMENT, T_SHIPMENT.ID, T_SHIPMENT.TOTAL) //
//        .values(shipmentId.toString(), shipment.getTotal()) //
//        .execute();
//    shipment.setId(shipmentId);
//    return shipment;
//  }
//
//  @Override
//  public void insertShipmentItems(final Shipment shipment) {
//    shipment.getItems().forEach(it -> {
//      dslContext.insertInto(T_ITEM, T_ITEM.ID, T_ITEM.QUANTITY, T_ITEM.F_PRODUCT_ID, T_ITEM.F_SHIPMENT_ID)
//          .values(UUID.randomUUID().toString(), it.getQuantity(), it.getProduct().getId().toString(), shipment.getId().toString()) //
//          .execute();
//    });
//  }
//
//  @Override
//  public Integer getShipmentCount() {
//    return dslContext.fetchCount(T_SHIPMENT);
//  }
//
//  @Override
//  public void clearShipments() {
//    dslContext.deleteFrom(T_SHIPMENT).execute();
//  }
}
