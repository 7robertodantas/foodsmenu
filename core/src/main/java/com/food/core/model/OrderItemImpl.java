package com.food.core.model;

import com.food.core.facade.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class OrderItemImpl implements OrderItem {

    @Getter
    private String item;

    @Getter
    private List<String> ingredients;

}
