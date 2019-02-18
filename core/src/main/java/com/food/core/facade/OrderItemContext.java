package com.food.core.facade;

import java.util.Map;

public interface OrderItemContext {

    double getCostPrice();

    Map<String, Double> getPricePerIngredient();

}
