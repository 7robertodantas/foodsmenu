package com.food.core.model;

import com.food.core.sales.SaleStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Menu {

    @Getter
    private final List<SaleStrategy> sales;

    @Getter
    private final List<MenuItem> menu;

}
