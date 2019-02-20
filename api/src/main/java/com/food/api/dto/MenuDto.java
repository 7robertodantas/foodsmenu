package com.food.api.dto;

import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

public class MenuDto {

    @Getter
    private final List<SaleStrategy> sales;

    @Getter
    private final List<MenuItemDto> items;

    @ConstructorProperties({"sales", "items"})
    public MenuDto(List<SaleStrategy> sales, List<MenuItemDto> items) {
        this.sales = sales;
        this.items = items;
    }

}
