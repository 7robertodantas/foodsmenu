package com.food.api.dto;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderItemContext;
import com.food.core.facade.OrderItem;
import com.food.core.sales.QuantitySaleStrategy;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.Optional;

public class QuantitySaleDto implements SaleStrategy {

    @Getter
    private final String description;

    @Getter
    private final String ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    @ConstructorProperties({"description", "ingredient", "forEachQuantityOf", "quantityThatWillBeFree"})
    public QuantitySaleDto(String description, String ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.description = description;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
    }

    @Override
    public Optional<Discount> apply(OrderItemContext context, OrderItem order) {
        return new QuantitySaleStrategy(description, ingredient, forEachQuantityOf, quantityThatWillBeFree).apply(context, order);
    }
}
