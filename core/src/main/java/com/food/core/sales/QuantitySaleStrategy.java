package com.food.core.sales;

import com.food.core.model.Discount;
import com.food.core.model.Ingredient;
import com.food.core.model.OrderItem;
import lombok.Getter;

import java.util.Optional;

public class QuantitySaleStrategy implements SaleStrategy {

    @Getter
    private final String description;

    @Getter
    private final Ingredient ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    public QuantitySaleStrategy(String description, Ingredient ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.description = description;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
        if(quantityThatWillBeFree > forEachQuantityOf){
            throw new IllegalStateException("The quantity that will be free can not be greater than the quantity that this discount strategy will be applied to.");
        }
    }

    @Override
    public Optional<Discount> apply(OrderItem order, double netOrderPrice) {

        long occurrences = order.getIngredients()
                .stream()
                .filter(i -> ingredient.getName().equalsIgnoreCase(i))
                .count();

        if (occurrences <= 0) {
            return Optional.empty();
        }

        final long howManyOcurrencesToDiscount = (occurrences / forEachQuantityOf) * quantityThatWillBeFree;

        if (howManyOcurrencesToDiscount <= 0) {
            return Optional.empty();
        }

        final double discountValue = howManyOcurrencesToDiscount * ingredient.getValue();
        return Optional.of(new Discount(description, discountValue));
    }

}
