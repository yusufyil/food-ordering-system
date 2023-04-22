package com.food.ordering.system.order.service.domain.domain.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity<ID> {
    private ID id;

}
