package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "ingredients"})
public class MenuItem {

    @Getter
    private final String name;

    @Getter
    private final List<Ingredient> ingredients;

    public double getNetPrice() {
        return ingredients.stream()
                .map(Ingredient::getValue)
                .reduce(Double::sum)
                .orElse(0d);
    }
}
