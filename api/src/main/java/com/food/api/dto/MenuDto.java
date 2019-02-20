package com.food.api.dto;

import com.food.core.facade.ItemsValues;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class MenuDto {

    @Getter
    private final Set<SaleStrategy> sales;

    @Getter
    private final List<MenuItemDto> items;

    @ConstructorProperties({"sales", "items"})
    public MenuDto(Set<SaleStrategy> sales, List<MenuItemDto> items) {
        this.sales = sales;
        this.items = items;
    }

    public MenuDto(MenuDescriptionDto description, ItemsValues itemsValues) {
        this.sales = description.getStrategies();
        this.items = itemsValues.getItems().stream().map(MenuItemDto::new).collect(toList());


    }
}
