package com.food.api.dto;

import com.food.core.facade.MenuItem;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

public class MenuItemDto implements MenuItem {

    @Getter
    private final String name;

    @Getter
    private final List<String> ingredients;

    @Getter
    private final double price;

    @ConstructorProperties({"name", "ingredients", "price"})
    public MenuItemDto(String name, List<String> ingredients, double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }
}
