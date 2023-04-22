package com.food.ordering.system.order.service.domain.dto.create;

import com.food.ordering.system.order.service.domain.domain.valueobject.CustomerID;
import com.food.ordering.system.order.service.domain.domain.valueobject.RestaurantID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {

    @NotNull
    private final CustomerID customerID;
    @NotNull
    private final RestaurantID restaurantID;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress orderAddress;
}
