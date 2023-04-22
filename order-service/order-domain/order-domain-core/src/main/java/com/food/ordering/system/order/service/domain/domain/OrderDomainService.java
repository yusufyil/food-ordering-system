package com.food.ordering.system.order.service.domain.domain;

import com.food.ordering.system.order.service.domain.domain.event.OrderCanceledEvent;
import com.food.ordering.system.order.service.domain.domain.entity.Order;
import com.food.ordering.system.order.service.domain.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCanceledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order, List<String> failureMessages);
}
