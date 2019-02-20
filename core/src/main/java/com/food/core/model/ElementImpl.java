package com.food.core.model;

import com.food.core.facade.Element;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ElementImpl implements Element {

    @Getter
    private final String name;

    @Getter
    private final double value;

}
