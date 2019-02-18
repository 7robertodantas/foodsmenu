package com.food.core.model;

import com.food.core.facade.Menu;
import com.food.core.facade.MenuItem;
import com.food.core.sales.SaleStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class MenuImpl implements Menu {

    @Getter
    private final List<SaleStrategy> sales;

    @Getter
    private final List<MenuItem> items;

}
