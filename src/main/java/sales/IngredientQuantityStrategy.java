package sales;

import lombok.Getter;
import model.Discount;
import model.Ingredient;
import model.OrderItem;

import java.util.Optional;

public class IngredientQuantityStrategy implements SaleStrategy {

    @Getter
    private final String name;

    @Getter
    private final Ingredient ingredient;

    @Getter
    private final int forEachQuantityOf;

    @Getter
    private final int quantityThatWillBeFree;

    public IngredientQuantityStrategy(String name, Ingredient ingredient, int forEachQuantityOf, int quantityThatWillBeFree) {
        this.name = name;
        this.ingredient = ingredient;
        this.forEachQuantityOf = forEachQuantityOf;
        this.quantityThatWillBeFree = quantityThatWillBeFree;
        if(quantityThatWillBeFree > forEachQuantityOf){
            throw new IllegalStateException("The quantity that will be free can not be greater than the quantity that this discount strategy will be applied to.");
        }
    }

    @Override
    public Optional<Discount> apply(OrderItem order, double netOrderPrice) {

        long quantity = order.getIngredients()
                .stream()
                .filter(i -> ingredient.getName().equalsIgnoreCase(i))
                .count();

        if (quantity <= 0) {
            return Optional.empty();
        }

        final long howManyDiscount = (quantity / forEachQuantityOf) * quantityThatWillBeFree;

        if (howManyDiscount <= 0) {
            return Optional.empty();
        }

        final double discountValue = howManyDiscount * ingredient.getValue();
        return Optional.of(new Discount(name, discountValue));
    }

}
