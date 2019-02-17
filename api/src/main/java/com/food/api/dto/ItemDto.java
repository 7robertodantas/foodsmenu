package com.food.api.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

public class ItemDto {

    @Getter
    private final String name;

    @Getter
    private final List<String> ingredients;

    @Getter
    private final double price;

    @ConstructorProperties({"name", "ingredients", "price"})
    public ItemDto(String name, List<String> ingredients, double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }
}
