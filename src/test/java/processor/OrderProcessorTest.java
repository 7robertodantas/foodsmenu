package processor;

import model.Discount;
import model.Order;
import model.OrderItem;
import model.Receipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sales.SaleStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class OrderProcessorTest {

    private Map<String, Double> pricePerIngredient;

    @BeforeEach
    private void setUp() {
        pricePerIngredient = new HashMap<>();
        pricePerIngredient.put("Lettuce", 0.40);
        pricePerIngredient.put("Bacon", 2.0);
        pricePerIngredient.put("Hamburguer", 3.0);
        pricePerIngredient.put("Egg", 0.80);
        pricePerIngredient.put("Cheese", 1.50);
    }

    @Test
    @DisplayName("Should reject unknown ingredients")
    public void shouldRejectUnknownIngredient() {
        OrderItem item = new OrderItem("X-Bacon", asList("Lettuce", "Rice", "Bacon")); // "Rice" is unknown
        Order order = new Order(singletonList(item));
        OrderProcessor orderProcessor = new OrderProcessor(emptyList(), pricePerIngredient);
        Assertions.assertThrows(IllegalStateException.class, () -> orderProcessor.process(order));
    }

    @Test
    @DisplayName("Should calculate price without sales strategies")
    public void shouldCalculatePriceWithoutSalesStrategies() {
        OrderItem itemEggBacon = new OrderItem("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"));
        OrderItem itemEgg = new OrderItem("X-Egg", asList("Lettuce", "Egg"));
        Order order = new Order(asList(itemEggBacon, itemEgg));
        double orderCostWithoutDiscount = calculateCostPrice(order);

        OrderProcessor orderProcessor = new OrderProcessor(emptyList(), pricePerIngredient);
        Receipt receipt = orderProcessor.process(order);

        assertThat(receipt.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getTotalPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).isEmpty()
        );
    }

    @Test
    @DisplayName("Should apply discount if price remains positive")
    public void shouldApplyDiscountIfPriceRemainsPositive() {
        final double percentage = 0.1;
        SaleStrategy off10 = (order, netOrderPrice) -> Optional.of(new Discount("10% OFF", netOrderPrice * percentage));

        OrderItem itemEggBacon = new OrderItem("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"));
        Order order = new Order(singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(order);

        OrderProcessor orderProcessor = new OrderProcessor(singletonList(off10), pricePerIngredient);
        Receipt receipt = orderProcessor.process(order);

        assertThat(receipt.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getTotalPrice()).isEqualTo(orderCostWithoutDiscount - (orderCostWithoutDiscount * percentage));
        assertThat(receipt.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).allSatisfy(discount -> assertThat(discount.getDescription()).isEqualTo("10% OFF"))
        );
    }

    @Test
    @DisplayName("Should not apply discount if price is going to be negative")
    public void shouldNotApplyDiscountIfPriceIsGoingToBeNegative() {
        final double percentage = 1.2;
        SaleStrategy off120percent = (order, netOrderPrice) -> Optional.of(new Discount("120% OFF", netOrderPrice * percentage));

        OrderItem itemEggBacon = new OrderItem("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"));
        Order order = new Order(singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(order);

        OrderProcessor orderProcessor = new OrderProcessor(singletonList(off120percent), pricePerIngredient);
        Receipt receipt = orderProcessor.process(order);

        assertThat(receipt.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getTotalPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).allSatisfy(discount -> assertThat(discount.getDescription()).isEqualTo("120% OFF"))
        );
    }

    @Test
    @DisplayName("Should apply multiples discounts until price remains positive")
    public void shouldApplyMultiplesDiscountsUntilPriceRemainsPositive() {
        SaleStrategy discountWholeValueMinusOne = (order, netOrderPrice) -> Optional.of(new Discount("Whole price - 1 discount", netOrderPrice-1));
        SaleStrategy discountWholeValueMinusTwo = (order, netOrderPrice) -> Optional.of(new Discount("Whole price - 2 discount", netOrderPrice-2));

        OrderItem itemEggBacon = new OrderItem("X-Egg-Bacon", asList("Lettuce", "Egg", "Bacon"));
        Order order = new Order(singletonList(itemEggBacon));
        double orderCostWithoutDiscount = calculateCostPrice(order);

        OrderProcessor orderProcessor = new OrderProcessor(asList(discountWholeValueMinusOne, discountWholeValueMinusTwo), pricePerIngredient);
        Receipt receipt = orderProcessor.process(order);

        assertThat(receipt.getCostPrice()).isEqualTo(orderCostWithoutDiscount);
        assertThat(receipt.getTotalPrice()).isBetween(orderCostWithoutDiscount - 2, orderCostWithoutDiscount - 1); // we can not assume the order in which the sales strategies were applied.
        assertThat(receipt.getItems()).allSatisfy((item) ->
                assertThat(item.getDiscounts()).hasSize(1) // only one of them should be applied, cause the other would give a negative price
        );
    }

    private double calculateCostPrice(Order order) {
        return order.getItems()
                .stream()
                .map(OrderItem::getIngredients)
                .map(this::calculateCostPrice)
                .reduce(Double::sum)
                .orElse(0.0d);
    }

    private double calculateCostPrice(List<String> ingredient) {
        return ingredient.stream()
                .map(pricePerIngredient::get)
                .reduce(Double::sum)
                .orElse(0.0d);
    }

}