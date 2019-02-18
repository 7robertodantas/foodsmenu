package com.food.core.model;

import com.food.core.facade.OrderContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class OrderContextImpl implements OrderContext {

    @Getter
    private double costPrice;

    @Getter
    private Map<String, Double> pricePerIngredient;

}
