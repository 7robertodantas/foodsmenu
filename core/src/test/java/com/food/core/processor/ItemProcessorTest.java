package com.food.core.processor;

import com.food.core.facade.*;
import com.food.core.model.DiscountImpl;
import com.food.core.model.ElementImpl;
import com.food.core.model.ItemImpl;
import com.food.core.model.ItemsContextImpl;
import com.food.core.sales.SaleStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static com.food.core.utils.CollectionUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class ItemProcessorTest {

    private Set<Element> elements;
    
    private Map<String, Double> valuePerElement;

    private ItemProcessor itemProcessor;

    @BeforeEach
    private void setUp() {

        elements = asSet(new ElementImpl("Lettuce", 0.4),
                new ElementImpl("Bacon", 2.0),
                new ElementImpl("Hamburguer", 3.0),
                new ElementImpl("Egg", 0.80),
                new ElementImpl("Cheese", 1.5));

        valuePerElement = elements.stream().collect(toMap(Element::getName, Element::getValue));
        itemProcessor = new ItemProcessor();
    }

    @Test
    @DisplayName("Should reject unknown ingredients")
    public void shouldRejectUnknownIngredient() {
        Item item = new ItemImpl("X-Bacon", asList("Lettuce", "Rice", "Bacon"), emptySet()); // "Rice" is unknown
        ItemsContext itemsContext = new ItemsContextImpl(emptySet(), emptySet(), singletonList(item));
        Assertions.assertThrows(IllegalStateException.class, () -> itemProcessor.process(itemsContext));
    }

    @Test
    @DisplayName("Should calculate price without com.food.core.sales strategies")
    public void shouldCalculatePriceWithoutSalesStrategies() {
        Item itemEggBacon = new ItemImpl("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"), emptySet());
        Item itemEgg = new ItemImpl("X-Egg", asList("Lettuce", "Egg"), emptySet());
        ItemsContext itemsContext = new ItemsContextImpl(elements, emptySet(), asList(itemEggBacon, itemEgg));
        double orderCostWithoutDiscount = calculateCostPrice(itemsContext);

        ItemsValues itemsValues = itemProcessor.process(itemsContext);

        assertThat(itemsValues.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getTotalPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).isEmpty()
        );
    }

    @Test
    @DisplayName("Should apply discount if price remains positive")
    public void shouldApplyDiscountIfPriceRemainsPositive() {
        final double percentage = 0.1;
        final SaleStrategy off10 = new SaleStrategy() {

            @Override
            public String getCode() {
                return "10off";
            }

            @Override
            public String getDescription() {
                return "10% OFF";
            }

            @Override
            public Optional<Discount> apply(ItemContext context) {
                return Optional.of(new DiscountImpl(getDescription(), context.getItemCostValue() * percentage));
            }
        };

        Item itemEggBacon = new ItemImpl("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"), asSet("10off"));
        ItemsContext itemsContext = new ItemsContextImpl(elements, asSet(off10), singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(itemsContext);

        ItemsValues itemsValues = itemProcessor.process(itemsContext);

        assertThat(itemsValues.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getTotalPrice()).isEqualTo(orderCostWithoutDiscount - (orderCostWithoutDiscount * percentage));
        assertThat(itemsValues.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).allSatisfy(discount -> assertThat(discount.getDescription()).isEqualTo("10% OFF"))
        );
    }

    @Test
    @DisplayName("Should not apply discount if price is going to be negative")
    public void shouldNotApplyDiscountIfPriceIsGoingToBeNegative() {
        final double percentage = 1.2;
        SaleStrategy off120percent = new SaleStrategy() {

            @Override
            public String getCode() {
                return "120off";
            }

            @Override
            public String getDescription() {
                return "120% OFF";
            }

            @Override
            public Optional<Discount> apply(ItemContext context) {
                return Optional.of(new DiscountImpl(getDescription(), context.getItemCostValue() * percentage));
            }
        };

        Item itemEggBacon = new ItemImpl("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"), asSet("120off"));
        ItemsContext itemsContext = new ItemsContextImpl(elements, new HashSet<>(singletonList(off120percent)), singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(itemsContext);

        ItemsValues itemsValues = itemProcessor.process(itemsContext);

        assertThat(itemsValues.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getTotalPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).allSatisfy(discount -> assertThat(discount.getDescription()).isEqualTo("120% OFF"))
        );
    }

    @Test
    @DisplayName("Should apply multiples discounts until price remains positive")
    public void shouldApplyMultiplesDiscountsUntilPriceRemainsPositive() {

        SaleStrategy discountWholeValueMinusOne = new SaleStrategy() {

            @Override
            public String getCode() {
                return "wholePriceDiscount1";
            }

            @Override
            public String getDescription() {
                return "Whole price - 1 discount";
            }

            @Override
            public Optional<Discount> apply(ItemContext context) {
                return Optional.of(new DiscountImpl(getDescription(), context.getItemCostValue() - 1.0));
            }
        };

        SaleStrategy discountWholeValueMinusTwo = new SaleStrategy() {

            @Override
            public String getCode() {
                return "wholePriceDiscount2";
            }

            @Override
            public String getDescription() {
                return "Whole price - 2 discount";
            }

            @Override
            public Optional<Discount> apply(ItemContext context) {
                return Optional.of(new DiscountImpl(getDescription(), context.getItemCostValue() - 2.0));
            }
        };

        Item itemEggBacon = new ItemImpl("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"), asSet("wholePriceDiscount1", "wholePriceDiscount2"));
        ItemsContext itemsContext = new ItemsContextImpl(elements, new HashSet<>(asList(discountWholeValueMinusOne, discountWholeValueMinusTwo)), singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(itemsContext);

        ItemsValues itemsValues = itemProcessor.process(itemsContext);

        //FIXME
        //Expecting:
        // <1.0>
        //to be between:
        // [1.2000000000000002, 2.2]

        assertThat(itemsValues.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(itemsValues.getTotalPrice()).isBetween(orderCostWithoutDiscount - 2.0, orderCostWithoutDiscount - 1.0); // we can not assume the order in which the com.food.core.sales strategies were applied.
        assertThat(itemsValues.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).hasSize(1) // only one of them should be applied, cause the other would give a negative price
        );
    }

    private double calculateCostPrice(ItemsContext itemsContext) {
        return itemsContext.getItems()
                .stream()
                .map(Item::getElements)
                .map(this::calculateCostPrice)
                .reduce(Double::sum)
                .orElse(0.0d);
    }

    private double calculateCostPrice(List<String> ingredient) {
        return ingredient.stream()
                .map(valuePerElement::get)
                .reduce(Double::sum)
                .orElse(0.0d);
    }

}