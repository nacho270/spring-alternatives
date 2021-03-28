package com.nacho.blog.springalternatives.jooqdemo.service;

import static com.nacho.blog.springalternatives.jooqdemo.gen.Tables.T_ITEM;
import static com.nacho.blog.springalternatives.jooqdemo.gen.Tables.T_PRODUCT;
import static com.nacho.blog.springalternatives.jooqdemo.gen.Tables.T_SHIPMENT;
import static org.jooq.impl.DSL.jsonArrayAgg;
import static org.jooq.impl.DSL.jsonObject;
import static org.jooq.impl.DSL.key;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jooq.DSLContext;

import com.nacho.blog.springalternatives.jooqdemo.model.Item;
import com.nacho.blog.springalternatives.jooqdemo.model.Product;
import com.nacho.blog.springalternatives.jooqdemo.model.Shipment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShipmentService {

  private final DSLContext context;
  private final ProductService productService;

  public Shipment createShipment(final List<ItemRequest> requestItems) {
    final Shipment shipment = mapShipment(requestItems);
    context.transaction(conf -> {
      try {
        insertShipment(shipment);
        insertItems(shipment);
      } catch (final Exception e) {
        throw new RuntimeException("Rolledback!!");
      }
    });
    return shipment;
  }

  private void insertItems(final Shipment shipment) {
    shipment.getItems().forEach(it -> {
      context.insertInto(T_ITEM, T_ITEM.ID, T_ITEM.QUANTITY, T_ITEM.F_PRODUCT_ID, T_ITEM.F_SHIPMENT_ID)
          .values(UUID.randomUUID().toString(), it.getQuantity(), it.getProduct().getId().toString(), shipment.getId().toString()) //
          .execute();
    });
  }

  private void insertShipment(final Shipment shipment) {
    final UUID shipmentId = UUID.randomUUID();
    context.insertInto(T_SHIPMENT, T_SHIPMENT.ID, T_SHIPMENT.TOTAL) //
        .values(shipmentId.toString(), shipment.getTotal()) //
        .execute();
    shipment.setId(shipmentId);
  }

  private Shipment mapShipment(final List<ItemRequest> requestItems) {
    final Shipment shipment = new Shipment();
    final List<Item> items = requestItems.stream() //
        .map(this::mapItem) //
        .collect(Collectors.toList());
    shipment.setItems(items);
    shipment.setTotal(items.stream()//
        .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity().longValue()))) //
        .reduce(BigDecimal.ZERO, BigDecimal::add));
    return shipment;
  }

  private Item mapItem(final ItemRequest ir) {
    final Product product = productService.getById(ir.getProduct());
    final Item item = Item.builder() //
        .id(UUID.randomUUID()) //
        .product(product) //
        .quantity(ir.getQuantity()) //
        .build();
    return item;
  }

  public Integer getShipmentCount() {
    return context.fetchCount(T_SHIPMENT);
  }

  public List<Shipment> getAll() {
    return context.select( //
        jsonObject( //
            key("id").value(T_SHIPMENT.ID), //
            key("total").value(T_SHIPMENT.TOTAL), //
            key("items").value( //
                jsonArrayAgg( //
                    jsonObject( //
                        key("id").value(T_ITEM.ID), //
                        key("quantity").value(T_ITEM.QUANTITY), //
                        key("product").value(jsonObject( //
                            key("id").value(T_PRODUCT.ID), //
                            key("name").value(T_PRODUCT.NAME), //
                            key("price").value(T_PRODUCT.PRICE) //
                        )) //
                    ) //
                )) //
        )) //
        .from(T_SHIPMENT) //
        .innerJoin(T_ITEM).on(T_ITEM.F_SHIPMENT_ID.eq(T_SHIPMENT.ID)) //
        .innerJoin(T_PRODUCT).on(T_ITEM.F_PRODUCT_ID.eq(T_PRODUCT.ID)) //
        .groupBy(T_SHIPMENT.ID) //
        .fetchInto(Shipment.class);
  }
}
