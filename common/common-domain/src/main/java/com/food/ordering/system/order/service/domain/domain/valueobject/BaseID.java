package com.food.ordering.system.order.service.domain.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class BaseID<T> {

    private final T value;

    protected BaseID(T value) {
        this.value = value;
    }
}
