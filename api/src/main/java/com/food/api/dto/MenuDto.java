package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.ItemsValues;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MenuDto {

    @Getter
    @JsonProperty("items")
    private final List<MenuItemDto> items;

    @Getter
    @JsonProperty("sales")
    private final Set<SaleDto> sales;

    @ConstructorProperties({"items", "sales"})
    public MenuDto(List<MenuItemDto> items, Set<SaleDto> sales) {
        this.items = items;
        this.sales = sales;
    }

    public MenuDto(MenuDescriptionDto description, ItemsValues itemsValues) {
        this.items = itemsValues.getItems()
                .stream()
                .map(MenuItemDto::new)
                .collect(toList());

        this.sales = description.getStrategies()
                .stream()
                .map(SaleStrategy::getDescription)
                .map(SaleDto::new)
                .collect(Collectors.toSet());
    }
}
