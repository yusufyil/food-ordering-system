package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.order.service.domain.entity.Order;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class OrderCanceledEvent extends OrderEvent {
    public OrderCanceledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
