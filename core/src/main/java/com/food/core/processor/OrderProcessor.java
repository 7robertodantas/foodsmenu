package com.food.core.processor;

import com.food.core.model.*;
import com.food.core.sales.SaleStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class OrderProcessor {

    @Getter
    private final List<SaleStrategy> strategies;

    @Getter
    private final Map<String, Double> pricePerIngredient;

    public Receipt process(Order order) {

        Set<String> rejectedIngredients = order.getItems()
                .stream()
                .map(OrderItem::getIngredients)
                .flatMap(List::stream)
                .filter(ingredient -> !pricePerIngredient.containsKey(ingredient))
                .collect(toSet());

        if(!rejectedIngredients.isEmpty()){
            throw new IllegalStateException("Unkown price of the given ingredients: " + String.join(",", rejectedIngredients));
        }

        final List<ReceiptItem> receiptItems = order.getItems()
                .stream()
                .map(this::processOrderItem)
                .collect(toList());

        final double costPrice = receiptItems
                .stream()
                .map(ReceiptItem::getCostPrice)
                .reduce(Double::sum)
                .orElse(0d);

        final double totalPrice = receiptItems
                .stream()
                .map(ReceiptItem::getTotalPrice)
                .reduce(Double::sum)
                .orElse(0d);

        return new Receipt(receiptItems, costPrice, totalPrice);
    }

    private ReceiptItem processOrderItem(final OrderItem item) {
        final double itemCostPrice = calculateCostPrice(item);
        return processOrderItem(item, itemCostPrice, itemCostPrice, new Stack<>(), strategies.stream().collect(Collectors.toCollection(Stack::new)));
    }

    private ReceiptItem processOrderItem(final OrderItem item,
                                         final double itemCostPrice,
                                         final double itemCurrentValue,
                                         final Stack<Discount> appliedDiscounts,
                                         final Stack<SaleStrategy> strategies) {

        if (strategies.isEmpty() || itemCurrentValue <= 0) {
            final double itemSellingPrice = Math.max(itemCurrentValue, 0);
            return new ReceiptItem(item, appliedDiscounts, itemCostPrice, itemSellingPrice);
        }

        final SaleStrategy discountStrategy = strategies.pop();
        final Optional<Discount> discount = discountStrategy.apply(item, itemCostPrice);
        final double newCurrentValue = discount.flatMap(d -> {
            final double newValue = itemCurrentValue - d.getValue();
            return newValue >= 0 ? Optional.of(newValue) : Optional.empty();
        }).orElse(itemCurrentValue);

        if(newCurrentValue != itemCurrentValue && discount.isPresent()){
            appliedDiscounts.push(discount.get());
        }

        return processOrderItem(item, itemCostPrice, newCurrentValue, appliedDiscounts, strategies);
    }

    private double calculateCostPrice(OrderItem orderItem){
        return orderItem.getIngredients()
                .stream()
                .map(pricePerIngredient::get)
                .reduce(Double::sum)
                .orElse(0.0d);
    }


}
