package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderItemContext;
import com.food.core.facade.OrderItem;
import com.food.core.model.DiscountImpl;
import lombok.Getter;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class CompositionSaleStrategy implements SaleStrategy {

    @Getter
    private final String description;

    @Getter
    private final double percentage;

    @Getter
    private final Set<String> shouldHave;

    @Getter
    private final Set<String> shouldNotHave;

    private final Predicate<OrderItem> containsAllRequiredIngredients;
    private final Predicate<OrderItem> doesnContainsSomeIngredients;
    private final Predicate<OrderItem> shouldApplyLightSale;

    public CompositionSaleStrategy(String description, double percentage, Set<String> shouldHave, Set<String> shouldNotHave) {
        this.description = description;
        this.percentage = percentage;
        this.shouldHave = shouldHave;
        this.shouldNotHave = shouldNotHave;
        this.containsAllRequiredIngredients = item ->
                item.getIngredients()
                        .stream()
                        .anyMatch(shouldHave::contains) || (!shouldHave.isEmpty() || !shouldNotHave.isEmpty());
        this.doesnContainsSomeIngredients = item ->
                item.getIngredients()
                        .stream()
                        .noneMatch(shouldNotHave::contains);
        this.shouldApplyLightSale = item ->
                containsAllRequiredIngredients.and(doesnContainsSomeIngredients).test(item);
    }

    @Override
    public Optional<Discount> apply(OrderItemContext context, OrderItem order) {
        if (shouldApplyLightSale.test(order)) {
            return Optional.of(new DiscountImpl(description, context.getCostPrice() * percentage));
        }
        return Optional.empty();
    }

}
