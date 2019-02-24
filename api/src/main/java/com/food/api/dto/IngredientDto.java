package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Element;
import lombok.Getter;

import java.beans.ConstructorProperties;

public class IngredientDto implements Element {

    @Getter
    @JsonProperty("name")
    private String name;

    @Getter
    @JsonProperty("value")
    private double value;

    @ConstructorProperties({"name", "value"})
    public IngredientDto(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
