package com.food.api.dto;

import com.food.core.facade.Element;
import lombok.Getter;

import java.beans.ConstructorProperties;

public class IngredientDto implements Element {

    @Getter
    private String name;

    @Getter
    private double value;

    @ConstructorProperties({"name", "value"})
    public IngredientDto(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
