package com.food.api.dto;

import com.food.core.facade.Discount;
import com.food.core.facade.ItemContext;
import com.food.core.sales.CompositionSaleStrategy;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.Optional;
import java.util.Set;

public class CompositionSaleDto implements SaleStrategy {

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

    @ConstructorProperties({"description", "percentage", "shouldHave", "shouldNotHave"})
    public CompositionSaleDto(String code, String description, double percentage, Set<String> shouldHave, Set<String> shouldNotHave) {
        this.code = code;
        this.description = description;
        this.percentage = percentage;
        this.shouldHave = shouldHave;
        this.shouldNotHave = shouldNotHave;
    }

    @Override
    public Optional<Discount> apply(ItemContext context) {
        return new CompositionSaleStrategy(code, description, percentage, shouldHave, shouldNotHave).apply(context);
    }
}
