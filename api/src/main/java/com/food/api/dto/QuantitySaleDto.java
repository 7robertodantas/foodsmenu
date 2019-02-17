package com.food.api.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;

public class QuantitySaleDto {

    @Getter
    private final String description;

    @Getter
    private final String ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    @ConstructorProperties({"description", "ingredient", "forEachQuantityOf", "quantityThatWillBeFree"})
    public QuantitySaleDto(String description, String ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.description = description;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
    }
}
