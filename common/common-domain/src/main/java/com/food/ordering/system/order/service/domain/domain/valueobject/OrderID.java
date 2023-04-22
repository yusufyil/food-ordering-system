package com.food.ordering.system.order.service.domain.domain.valueobject;

import java.util.UUID;

public class OrderID extends BaseID<UUID> {

    public OrderID(UUID value) {
        super(value);
    }
}
