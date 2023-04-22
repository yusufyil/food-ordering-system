package com.food.ordering.system.order.service.domain.domain.entity;

import com.food.ordering.system.order.service.domain.domain.entitiy.BaseEntity;
import com.food.ordering.system.order.service.domain.domain.valueobject.OrderItemID;
import com.food.ordering.system.order.service.domain.domain.valueobject.Money;
import com.food.ordering.system.order.service.domain.domain.valueobject.OrderID;
import lombok.Getter;

@Getter
public class OrderItem extends BaseEntity<OrderItemID> {
    private OrderID orderID;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    void initializeOrderItem(OrderID orderID, OrderItemID orderItemID) {
        this.orderID = orderID;
        super.setId(orderItemID);
    }

    boolean isPriceValid(){
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }
    private OrderItem(Builder builder) {
        super.setId(builder.orderItemID);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public static final class Builder {
        private OrderItemID orderItemID;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderItemID(OrderItemID val) {
            orderItemID = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
