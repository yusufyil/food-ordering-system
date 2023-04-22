package com.food.ordering.system.order.service.domain.domain.entity;

import com.food.ordering.system.order.service.domain.domain.entitiy.BaseEntity;
import com.food.ordering.system.order.service.domain.domain.valueobject.Money;
import com.food.ordering.system.order.service.domain.domain.valueobject.ProductID;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductID> {
    private String name;
    private Money price;

    public Product(ProductID productID) {
        super.setId(productID);
    }

    public Product(ProductID id, String name, Money price) {
        super.setId(id);
        this.name = name;
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
