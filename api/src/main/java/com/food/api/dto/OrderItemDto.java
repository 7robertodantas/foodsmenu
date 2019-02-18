package com.food.api.dto;

import com.food.core.facade.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class OrderItemDto implements OrderItem {

    @Getter
    private String item;

    @Getter
    private List<String> ingredients;

}
