package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.order.service.domain.entitiy.BaseEntity;
import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.order.service.domain.valueobject.ProductID;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductID>{
    private String name;
    private Money price;

    public Product(ProductID id, String name, Money price) {
        super.setId(id);
        this.name = name;
        this.price = price;
    }
}
