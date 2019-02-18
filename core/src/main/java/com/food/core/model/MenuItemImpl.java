package com.food.core.model;

import com.food.core.facade.MenuItem;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "ingredients"})
public class MenuItemImpl implements MenuItem {

    @Getter
    private final String name;

    @Getter
    private final List<String> ingredients;

}
