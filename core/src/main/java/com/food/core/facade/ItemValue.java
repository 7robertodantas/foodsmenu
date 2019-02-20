package com.food.core.facade;

import java.util.List;

public interface ItemValue {

    Item getItem();

    List<Discount> getDiscounts();

    double getCostValue();

    double getTotalValue();

}
