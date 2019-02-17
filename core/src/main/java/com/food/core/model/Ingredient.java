package com.food.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Ingredient {

    @Getter
    private final String name;

    @Getter
    private final double value;
}

