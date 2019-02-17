package com.food.core.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@EqualsAndHashCode(of = {"items"})
public class Order {

    @Getter
    private final List<OrderItem> items;

}
