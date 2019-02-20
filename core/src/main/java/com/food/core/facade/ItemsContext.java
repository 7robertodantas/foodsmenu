package com.food.core.facade;

import com.food.core.sales.SaleStrategy;

import java.util.List;
import java.util.Set;

public interface ItemsContext {

    Set<Element> getElements();

    Set<SaleStrategy> getStrategies();

    List<Item> getItems();

}
