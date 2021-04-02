package com.nacho.blog.springalternatives.fulldemo.repository.impl;

import com.nacho.blog.springalternatives.fulldemo.model.Item;
import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

import static com.nacho.blog.springalternatives.fulldemo.gen.Tables.*;
import static org.jooq.impl.DSL.*;

@Singleton
public class ShipmentRepositoryImpl implements ShipmentRepository {

  private final DSLContext dslContext;

  @Inject
  public ShipmentRepositoryImpl(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  @Override
  public Shipment getById(final UUID id) {
    return dslContext.select( //
            jsonObject( //
                    key("id").value(T_SHIPMENT.ID), //
                    key("total").value(T_SHIPMENT.TOTAL), //
                    key("user").value( //
                            jsonObject( //
                                    key("id").value(T_USER.ID), //
                                    key("email").value(T_USER.EMAIL) //
                            )
                    ),
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
            .innerJoin(T_USER).on(T_SHIPMENT.F_USER_ID.eq(T_USER.ID)) //
            .innerJoin(T_ITEM).on(T_ITEM.F_SHIPMENT_ID.eq(T_SHIPMENT.ID)) //
            .innerJoin(T_PRODUCT).on(T_ITEM.F_PRODUCT_ID.eq(T_PRODUCT.ID)) //
            .where(T_SHIPMENT.ID.equal(id.toString())) //
            .groupBy(T_SHIPMENT.ID) //
            .fetchOneInto(Shipment.class);
  }

  @Override
  public Shipment insertShipment(final Shipment shipment) {
    final UUID shipmentId = UUID.randomUUID();
    dslContext.insertInto(T_SHIPMENT, T_SHIPMENT.ID, T_SHIPMENT.TOTAL, T_SHIPMENT.F_USER_ID) //
            .values(shipmentId.toString(), shipment.getTotal(), shipment.getUser().getId()) //
            .execute();
    shipment.setId(shipmentId);
    return shipment;
  }

  @Override
  public void insertShipmentItems(final Shipment shipment) {
    shipment.getItems().forEach(item -> insertItem(item, shipment));
  }

  private void insertItem(Item it, Shipment shipment) {
    dslContext.insertInto(T_ITEM, T_ITEM.ID, T_ITEM.QUANTITY, T_ITEM.F_PRODUCT_ID, T_ITEM.F_SHIPMENT_ID) //
            .values(UUID.randomUUID().toString(), it.getQuantity(), it.getProduct().getId().toString(), shipment.getId().toString()) //
            .execute();
  }

  @Override
  public Integer getShipmentCount() {
    return dslContext.fetchCount(T_SHIPMENT);
  }

  @Override
  public void clearShipments() {
    dslContext.deleteFrom(T_SHIPMENT).execute();
  }
}
