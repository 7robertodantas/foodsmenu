package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.Item;
import com.food.core.facade.ItemContext;
import com.food.core.model.DiscountImpl;
import lombok.Getter;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class CompositionSaleStrategy implements SaleStrategy {

    @Getter
    private final String code;

    @Getter
    private final String description;

    @Getter
    private final double percentage;

    @Getter
    private final Set<String> shouldHave;

    @Getter
    private final Set<String> shouldNotHave;

    private final Predicate<Item> containsAllRequiredIngredients;
    private final Predicate<Item> doesnContainsSomeIngredients;
    private final Predicate<Item> shouldApplyLightSale;

    public CompositionSaleStrategy(String code, String description, double percentage, Set<String> shouldHave, Set<String> shouldNotHave) {
        this.code = code;
        this.description = description;
        this.percentage = percentage;
        this.shouldHave = shouldHave;
        this.shouldNotHave = shouldNotHave;
        this.containsAllRequiredIngredients = item ->
                item.getElements()
                        .stream()
                        .anyMatch(shouldHave::contains) || (!shouldHave.isEmpty() || !shouldNotHave.isEmpty());
        this.doesnContainsSomeIngredients = item ->
                item.getElements()
                        .stream()
                        .noneMatch(shouldNotHave::contains);
        this.shouldApplyLightSale = item ->
                containsAllRequiredIngredients.and(doesnContainsSomeIngredients).test(item);
    }

    @Override
    public Optional<Discount> apply(ItemContext context) {
        if (shouldApplyLightSale.test(context.getItem())) {
            return Optional.of(new DiscountImpl(description, context.getItemCostValue() * percentage));
        }
        return Optional.empty();
    }

}
