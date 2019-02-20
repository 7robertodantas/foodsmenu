package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.core.facade.Item;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Set;

public class ItemDto implements Item {

    @Getter
    private String name;

    @Getter
    private List<String> ingredients;

    @Getter
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
