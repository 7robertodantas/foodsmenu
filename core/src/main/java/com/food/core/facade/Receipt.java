package com.food.core.facade;

import java.util.List;

public interface Receipt {

    List<ReceiptItem> getItems();

    double getCostPrice();

    double getTotalPrice();

}
