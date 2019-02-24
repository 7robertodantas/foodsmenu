package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Item;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Set;

public class ItemDto implements Item {

    @Getter
    @JsonProperty("name")
    private String name;

    @Getter
    @JsonProperty("ingredients")
    private List<String> ingredients;

    @Getter
    @JsonProperty("eligibleSalesCodes")
    private Set<String> eligibleSalesCodes;

    @ConstructorProperties({"name", "ingredients", "eligibleSalesCodes"})
    public ItemDto(String name, List<String> ingredients, Set<String> eligibleSalesCodes) {
        this.name = name;
        this.ingredients = ingredients;
        this.eligibleSalesCodes = eligibleSalesCodes;
    }

    @JsonIgnore
    @Override
    public List<String> getElements() {
        return ingredients;
    }
}
