package com.food.api.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;

public class QuantitySaleDto {

    @Getter
    private final String name;

    @Getter
    private final String ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    @ConstructorProperties({"name", "ingredient", "forEachQuantityOf", "quantityThatWillBeFree"})
    public QuantitySaleDto(String name, String ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.name = name;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
    }
}
