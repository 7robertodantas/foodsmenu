package com.food.core.facade;

import java.util.List;

public interface ReceiptItem {

    OrderItem getItem();

    List<Discount> getDiscounts();

    double getCostPrice();

    double getTotalPrice();

}
