package com.food.core.model;

import com.food.core.facade.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IngredientImpl implements Ingredient {

    @Getter
    private final String name;

    @Getter
    private final double value;
}

