package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Item;
import com.food.core.facade.ItemValue;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Set;

public class MenuItemDto implements Item {

    @Getter
    @JsonProperty("name")
    private final String name;

    @Getter
    @JsonProperty("ingredients")
    private final List<String> ingredients;

    @Getter
    @JsonProperty("price")
    private final double price;

    @Getter
    @JsonIgnore
    private final Set<String> eligibleSalesCodes;

    @ConstructorProperties({"name", "ingredients", "price", "eligibleSalesCodes"})
    public MenuItemDto(String name, List<String> ingredients, double price, Set<String> eligibleSalesCodes) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.eligibleSalesCodes = eligibleSalesCodes;
    }

    public MenuItemDto(ItemValue itemValue) {
        this.name = itemValue.getItem().getName();
        this.ingredients = itemValue.getItem().getElements();
        this.price = itemValue.getTotalValue();
        this.eligibleSalesCodes = itemValue.getItem().getEligibleSalesCodes();
    }

    @JsonIgnore
    public List<String> getElements(){
        return ingredients;
    }

}
