package com.food.core.processor;

import com.food.core.facade.*;
import com.food.core.model.ItemContextImpl;
import com.food.core.model.ItemValueImpl;
import com.food.core.model.ItemsValuesImpl;
import com.food.core.sales.SaleStrategy;
import lombok.AllArgsConstructor;

import java.util.*;

import static java.util.stream.Collectors.*;

@AllArgsConstructor
public class ItemProcessor {

    public ItemsValues process(final ItemsContext itemsContext) {

        final Map<String, Double> valuePerElement = itemsContext.getElements()
                .stream()
                .collect(toMap(Element::getName, Element::getValue));

        final Set<String> rejectedIngredients = itemsContext.getItems()
                .stream()
                .map(Item::getElements)
                .flatMap(List::stream)
                .filter(ingredient -> !valuePerElement.containsKey(ingredient))
                .collect(toSet());

        if(!rejectedIngredients.isEmpty()){
            throw new IllegalStateException("Unknown value for the given elements: " + String.join(",", rejectedIngredients));
        }

        final Set<SaleStrategy> strategies = itemsContext.getStrategies();

        final List<ItemValue> itemValues = itemsContext.getItems()
                .stream()
                .map(item -> processItem(item, strategies, valuePerElement))
                .collect(toList());

        final double costValue = itemValues
                .stream()
                .map(ItemValue::getCostValue)
                .reduce(Double::sum)
                .orElse(0d);

        final double totalValue = itemValues
                .stream()
                .map(ItemValue::getTotalValue)
                .reduce(Double::sum)
                .orElse(0d);

        return new ItemsValuesImpl(itemValues, costValue, totalValue);
    }

    private ItemValue processItem(final Item item,
                                  final Set<SaleStrategy> strategies,
                                  final Map<String, Double> valuePerElement) {
        final double itemCostValue = calculateCostValue(item, valuePerElement);
        final ItemContext context = new ItemContextImpl(item, itemCostValue, valuePerElement);
        final Stack<Discount> appliedDiscounts = new Stack<>();
        final Stack<SaleStrategy> salesStrategies = strategies
                .stream()
                .filter(s -> item.getEligibleSalesCodes().contains(s.getCode()))
                .collect(toCollection(Stack::new));
        return processItem(context, itemCostValue, appliedDiscounts, salesStrategies);
    }

    private ItemValue processItem(final ItemContext context,
                                  final double currentItemValue,
                                  final Stack<Discount> appliedDiscounts,
                                  final Stack<SaleStrategy> strategies) {

        if (strategies.isEmpty() || currentItemValue <= 0) {
            return new ItemValueImpl(context.getItem(), appliedDiscounts, context.getItemCostValue(), currentItemValue);
        }

        final SaleStrategy discountStrategy = strategies.pop();
        final Optional<Discount> discount = discountStrategy.apply(context);
        final double newCurrentValue = discount.flatMap(d -> {
            final double newValue = currentItemValue - d.getValue();
            return newValue >= 0 ? Optional.of(newValue) : Optional.empty();
        }).orElse(currentItemValue);

        if(newCurrentValue != currentItemValue && discount.isPresent()){
            appliedDiscounts.push(discount.get());
        }

        return processItem(context, newCurrentValue, appliedDiscounts, strategies);
    }

    private double calculateCostValue(final Item item,
                                      final Map<String, Double> valuePerElement){
        return item.getElements()
                .stream()
                .map(valuePerElement::get)
                .reduce(Double::sum)
                .orElse(0.0d);
    }


}
