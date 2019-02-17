package com.food.core.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(of = {"description", "value"})
@ToString
public class Discount {

    @Getter
    private final String description;

    @Getter
    private final double value;
}
