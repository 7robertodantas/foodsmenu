package com.food.core.model;

import com.food.core.facade.Item;
import com.food.core.facade.ItemContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ItemContextImpl implements ItemContext {

    @Getter
    private Item item;

    @Getter
    private double itemCostValue;

    @Getter
    private Map<String, Double> valuePerElement;

}
