package com.food.ordering.system.order.service.domain.domain.entity;

import com.food.ordering.system.order.service.domain.domain.entitiy.AggregateRoot;
import com.food.ordering.system.order.service.domain.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.valueobject.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Order extends AggregateRoot<OrderID> {
    private final CustomerID customerID;
    private final RestaurantID restaurantID;

    private final StreetAddress deliveryAddress;

    private final Money price;

    private final List<OrderItem> items;

    private TrackingID trackingID;

    private OrderStatus orderStatus;

    private List<String> failureMessages;

    public void initializeOrder() {
        setId(new OrderID(UUID.randomUUID()));
        trackingID = new TrackingID(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order status is not in correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order status is not in correct state for approve operation");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order status is not in correct state for init cancel operation");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus != OrderStatus.PENDING || orderStatus != OrderStatus.CANCELLING)) {
            throw new OrderDomainException("Order status is not in correct state for cancel operation");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization.");
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Total price has to be greater than zero.");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: " + price.getAmount() + " is not equal to total!" + orderItemsTotal.getAmount());
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price" + orderItem.getPrice() + " is not valid for product " +
                    orderItem.getProduct().getId().getValue());
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem : items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemID(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderID);
        customerID = builder.customerID;
        restaurantID = builder.restaurantID;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.orderItems;
        trackingID = builder.trackingID;
        orderStatus = builder.status;
        failureMessages = builder.failureMessages;
    }


    public static final class Builder {
        private OrderID orderID;
        private CustomerID customerID;
        private RestaurantID restaurantID;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> orderItems;
        private TrackingID trackingID;
        private OrderStatus status;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderID(OrderID val) {
            orderID = val;
            return this;
        }

        public Builder customerID(CustomerID val) {
            customerID = val;
            return this;
        }

        public Builder restaurantID(RestaurantID val) {
            restaurantID = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder trackingID(TrackingID val) {
            trackingID = val;
            return this;
        }

        public Builder status(OrderStatus val) {
            status = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
