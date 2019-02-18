package com.food.core.facade;

import com.food.core.sales.SaleStrategy;

import java.util.List;

public interface Menu {

    List<SaleStrategy> getSales();

    List<MenuItem> getItems();

}
