package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.beans.ConstructorProperties;

public class SaleDto  {

    @Getter
    @JsonProperty("description")
    private final String description;

    @ConstructorProperties({"description"})
    public SaleDto(String description) {
        this.description = description;
    }

}
