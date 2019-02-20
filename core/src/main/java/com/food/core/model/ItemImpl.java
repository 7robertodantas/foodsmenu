package com.food.core.model;

import com.food.core.facade.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@ToString
public class ItemImpl implements Item {

    @Getter
    private final String name;

    @Getter
    private final List<String> elements;

    @Getter
    private final Set<String> eligibleSalesCodes;

}
