package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.ItemContext;
import com.food.core.facade.Item;

import java.util.Optional;

public interface SaleStrategy {

    String getCode();

    String getDescription();

    Optional<Discount> apply(ItemContext context);

}
