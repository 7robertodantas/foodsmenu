package com.food.core.facade;

import java.util.Map;

public interface ItemContext {

    Item getItem();

    double getItemCostValue();

    Map<String, Double> getValuePerElement();

}
