package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderItemContext;
import com.food.core.facade.OrderItem;
import com.food.core.model.DiscountImpl;
import lombok.Getter;

import java.util.Optional;

public class QuantitySaleStrategy implements SaleStrategy {

    @Getter
    private final String description;

    @Getter
    private final String ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    public QuantitySaleStrategy(String description, String ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.description = description;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
        if(quantityThatWillBeFree > forEachQuantityOf){
            throw new IllegalStateException("The quantity that will be free can not be greater than the quantity that this discount strategy will be applied to.");
        }
    }

    @Override
    public Optional<Discount> apply(OrderItemContext context, OrderItem order) {

        long occurrences = order.getIngredients()
                .stream()
                .filter(ingredient::equalsIgnoreCase)
                .count();

        if (occurrences <= 0) {
            return Optional.empty();
        }

        final long howManyOcurrencesToDiscount = (occurrences / forEachQuantityOf) * quantityThatWillBeFree;

        if (howManyOcurrencesToDiscount <= 0) {
            return Optional.empty();
        }

        final double discountValue = howManyOcurrencesToDiscount * context.getPricePerIngredient().get(ingredient);
        return Optional.of(new DiscountImpl(description, discountValue));
    }

}
