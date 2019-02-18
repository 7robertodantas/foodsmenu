package com.food.core.facade;

import java.util.Map;

public interface OrderContext {

    double getCostPrice();

    Map<String, Double> getPricePerIngredient();

}
