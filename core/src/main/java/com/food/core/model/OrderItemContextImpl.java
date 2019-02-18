package com.food.core.model;

import com.food.core.facade.OrderItemContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class OrderItemContextImpl implements OrderItemContext {

    @Getter
    private double costPrice;

    @Getter
    private Map<String, Double> pricePerIngredient;

}
