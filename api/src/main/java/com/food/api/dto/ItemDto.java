package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.core.facade.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ItemDto implements Item {

    @Getter
    private String name;

    @Getter
    private List<String> ingredients;

    @Getter
    private Set<String> eligibleSalesCodes;

    @JsonIgnore
    @Override
    public List<String> getElements() {
        return ingredients;
    }
}
