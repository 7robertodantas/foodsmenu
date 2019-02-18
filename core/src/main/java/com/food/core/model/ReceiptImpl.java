package com.food.core.model;

import com.food.core.facade.Receipt;
import com.food.core.facade.ReceiptItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ReceiptImpl implements Receipt {

    @Getter
    private final List<ReceiptItem> items;

    @Getter
    private final double costPrice;

    @Getter
    private final double totalPrice;

}
