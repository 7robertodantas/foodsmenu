package com.food.api.dto;

import com.food.core.facade.Order;
import com.food.core.facade.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class OrderDto implements Order {

    @Getter
    private List<OrderItem> items;

}
