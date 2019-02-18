package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderContext;
import com.food.core.facade.OrderItem;

import java.util.Optional;

public interface SaleStrategy {

    String getDescription();

    Optional<Discount> apply(OrderContext context, OrderItem order);

}
