package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.domain.OrderDomainService;
import com.food.ordering.system.order.service.domain.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.domain.entity.Order;
import com.food.ordering.system.order.service.domain.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerID());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private void checkCustomer(UUID customerID) {
        Optional<Customer> customer = customerRepository.findCustomer(customerID);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id: {}.", customerID);
            throw new OrderDomainException("Could not find customer with id: " + customerID);
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(restaurant);
        if (restaurantInformation.isEmpty()){
            log.warn("Could not find restaurant with id: {}", createOrderCommand.getRestaurantID());
            throw new OrderDomainException("Could not find restaurant with id: " + createOrderCommand.getRestaurantID());
        }
        return restaurantInformation.get();
    }

    private Order saveOrder(Order order){
        Order orderResult = orderRepository.save(order);
        if (orderResult == null){
            log.error("Order could not saved with id: {}", order.getId().getValue());
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
