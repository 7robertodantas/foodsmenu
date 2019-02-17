package com.food.core.sales;

import com.food.core.model.Discount;
import com.food.core.model.OrderItem;

import java.util.Optional;

public interface SaleStrategy {

    Optional<Discount> apply(OrderItem order, double netOrderPrice);

}
