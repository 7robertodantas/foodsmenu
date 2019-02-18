package com.food.core.model;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderItem;
import com.food.core.facade.ReceiptItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ReceiptItemImpl implements ReceiptItem {

    @Getter
    private final OrderItem item;

    @Getter
    private final List<Discount> discounts;

    @Getter
    private final double costPrice;

    @Getter
    private final double totalPrice;

}
