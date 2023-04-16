package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.order.service.domain.entitiy.AggregateRoot;
import com.food.ordering.system.order.service.domain.valueobject.RestaurantID;
import lombok.Getter;

import java.util.List;

@Getter
public class Restaurant extends AggregateRoot<RestaurantID> {
    private final List<Product> products;
    private boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.restaurantID);
        products = builder.products;
        active = builder.active;
    }


    public static final class Builder {
        private RestaurantID restaurantID;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder restaurantID(RestaurantID val) {
            restaurantID = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
