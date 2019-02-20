package com.food.core.model;

import com.food.core.facade.ItemsValues;
import com.food.core.facade.ItemValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ItemsValuesImpl implements ItemsValues {

    @Getter
    private final List<ItemValue> items;

    @Getter
    private final double costPrice;

    @Getter
    private final double totalPrice;

}
