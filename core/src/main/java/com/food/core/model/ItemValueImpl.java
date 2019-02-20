package com.food.core.model;

import com.food.core.facade.Discount;
import com.food.core.facade.Item;
import com.food.core.facade.ItemValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ItemValueImpl implements ItemValue {

    @Getter
    private final Item item;

    @Getter
    private final List<Discount> discounts;

    @Getter
    private final double costValue;

    @Getter
    private final double totalValue;

}
