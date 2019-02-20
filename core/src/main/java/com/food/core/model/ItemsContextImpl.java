package com.food.core.model;

import com.food.core.facade.Element;
import com.food.core.facade.Item;
import com.food.core.facade.ItemsContext;
import com.food.core.sales.SaleStrategy;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@EqualsAndHashCode(of = {"items"})
public class ItemsContextImpl implements ItemsContext {

    @Getter
    private final Set<Element> elements;

    @Getter
    private final Set<SaleStrategy> strategies;

    @Getter
    private final List<Item> items;

}
