package com.food.api.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.Set;

public class CompositionSaleDto {

    @Getter
    private final String description;

    @Getter
    private final double percentage;

    @Getter
    private final Set<String> shouldHave;

    @Getter
    private final Set<String> shouldNotHave;

    @ConstructorProperties({"description", "percentage", "shouldHave", "shouldNotHave"})
    public CompositionSaleDto(String description, double percentage, Set<String> shouldHave, Set<String> shouldNotHave) {
        this.description = description;
        this.percentage = percentage;
        this.shouldHave = shouldHave;
        this.shouldNotHave = shouldNotHave;
    }
}
