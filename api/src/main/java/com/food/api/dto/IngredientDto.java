package com.food.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class IngredientDto {

    @Getter
    private final String name;

    @Getter
    private final double price;

}
