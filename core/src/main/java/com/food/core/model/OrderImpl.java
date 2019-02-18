package com.food.core.model;

import com.food.core.facade.Order;
import com.food.core.facade.OrderItem;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@EqualsAndHashCode(of = {"items"})
public class OrderImpl implements Order {

    @Getter
    private final List<OrderItem> items;

}
