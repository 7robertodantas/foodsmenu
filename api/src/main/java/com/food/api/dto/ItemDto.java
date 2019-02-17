package com.food.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
public class ItemDto {

    @Getter
    private final String name;

    @Getter
    private final List<String> ingredients;

    @Getter
    private final double price;

}
