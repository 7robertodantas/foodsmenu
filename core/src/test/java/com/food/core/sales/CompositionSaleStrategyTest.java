package com.food.core.sales;

import com.food.core.model.Discount;
import com.food.core.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class CompositionSaleStrategyTest {

    @DisplayName("Should get the correct discount")
    @ParameterizedTest(name = "{index} => For an item having {1} and not having {2}, given {0} with net price of {3} should expect discount of {5}")
    @MethodSource("ingredientCompositionProvider")
    public void testApply(OrderItem order,
                          Set<String> shouldHave,
                          Set<String> shouldNotHave,
                          double netPrice,
                          double percentage,
                          double expectedDiscount) {


        CompositionSaleStrategy strategy = new CompositionSaleStrategy("", percentage, shouldHave, shouldNotHave);
        Optional<Discount> discount = strategy.apply(order, netPrice);

        if (expectedDiscount > 0.0) {
            assertThat(discount).isPresent();
            assertThat(discount.get().getValue()).isEqualTo(expectedDiscount);
        } else {
            assertThat(discount).isEmpty();
        }
    }

    private static Stream<Arguments> ingredientCompositionProvider() {
        final double percentage = 0.1;
        return Stream.of(
            // order items, should have, should not have,
            Arguments.of(new OrderItem("X-Bacon", singletonList("Bacon")), emptySet(), emptySet(), 10, percentage, 0.0), // no elements expected should return no discount
            Arguments.of(new OrderItem("X-Bacon", singletonList("Bacon")), setOf("Bacon"), emptySet(), 10, percentage, 10 * percentage), // should have one element
            Arguments.of(new OrderItem("X-Bacon", singletonList("Bacon")), emptySet(), setOf("Bacon"), 10, percentage, 0.0), // should not have one element
            Arguments.of(new OrderItem("X-Bacon", singletonList("Bread")), emptySet(), setOf("Bacon"), 10, percentage, 10 * percentage) // should not have one element
        );
    }

    private static Set<String> setOf(String ... values) {
        return Arrays.stream(values).collect(toSet());
    }

}