package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderItemContext;
import com.food.core.facade.OrderItem;
import com.food.core.model.OrderItemContextImpl;
import com.food.core.model.OrderItemImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class QuantitySaleStrategyTest {

    @DisplayName("Should get the correct discount")
    @ParameterizedTest(name = "{index} => For each group of {0}, {1} should be free. Given {2} with value of {3} then the expected discount is {4}")
    @MethodSource("ingredientQuantityProvider")
    public void testApply(int forEachQuantityOf,
                          int quantityThatWillBeFree,
                          int howManyInOrder,
                          double valueOfEach,
                          double expectedDiscount) {

        OrderItem order = new OrderItemImpl("Item", IntStream.range(0, howManyInOrder).boxed()
                .map(i -> "Ingredient").collect(toList()));

        String description = String.format("For each %s Ingredient pay only %s", forEachQuantityOf, forEachQuantityOf - quantityThatWillBeFree);

        Map<String, Double> pricePerIngredient = new HashMap<>();
        pricePerIngredient.put("Ingredient", valueOfEach);

        OrderItemContext orderItemContext = new OrderItemContextImpl(order.getIngredients().size() * valueOfEach, pricePerIngredient);
        QuantitySaleStrategy strategy = new QuantitySaleStrategy(description, "Ingredient", forEachQuantityOf, quantityThatWillBeFree);
        Optional<Discount> discount = strategy.apply(orderItemContext, order);

        if (expectedDiscount > 0.0) {
            assertThat(discount).isPresent();
            assertThat(discount.get().getValue()).isEqualTo(expectedDiscount);
        } else {
            assertThat(discount).isEmpty();
        }

    }

    @Test
    @DisplayName("Should not allow free quantity greater than each quantity that will be applied.")
    public void testIllegalArgument() {
        Assertions.assertThrows(IllegalStateException.class, () ->
                new QuantitySaleStrategy("", "Ingredient", 3, 4));
    }

    private static Stream<Arguments> ingredientQuantityProvider() {
        return Stream.of(
                ingredientsQuantityargumentBase(10.0),
                ingredientQuantityArgument(2, 1, 10.0),
                ingredientQuantityArgument(3, 1, 2.12),
                ingredientQuantityArgument(7, 3, 4.75),
                ingredientQuantityArgument(11, 3, 1.25)
        ).flatMap(Function.identity());
    }

    private static Stream<Arguments> ingredientsQuantityargumentBase(double valueOfEach) {
        return Stream.of(
                // forEachQuantityOf, quantityThatWillBeFree, howManyInOrder, valueOfEach, expectedDiscount
                Arguments.of(1, 1, 0, valueOfEach, 0.0), // zero quantity should return no discount
                Arguments.of(1, 0, 1, valueOfEach, 0.0), // 0 will be free should return no discount
                Arguments.of(1, 1, 1, valueOfEach, valueOfEach) // each will be free
        );
    }

    private static Stream<Arguments> ingredientQuantityArgument(int forEachQuantityOf, int quantityThatWillBeFree, double valueOfEach) {
        return Stream.of(
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, 0, valueOfEach, 0.0), // zero quantity should return no discount
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, forEachQuantityOf - 1, valueOfEach, 0.0), // less the quantity
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, forEachQuantityOf + 1, valueOfEach, valueOfEach * quantityThatWillBeFree), // 1 above the quantity to apply discount
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, forEachQuantityOf * 2, valueOfEach, valueOfEach * quantityThatWillBeFree * 2), // twice the quantity to apply discount
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, forEachQuantityOf * 3, valueOfEach, valueOfEach * quantityThatWillBeFree * 3), // thrice the quantity to apply discount
                Arguments.of(forEachQuantityOf, quantityThatWillBeFree, (forEachQuantityOf * 3) - 1, valueOfEach, valueOfEach * quantityThatWillBeFree * 2) // twice the quantity to apply discount and one reaming
        );
    }

}