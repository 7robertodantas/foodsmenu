package com.food.api.dto;

import com.food.core.facade.Discount;
import com.food.core.facade.OrderContext;
import com.food.core.facade.OrderItem;
import com.food.core.sales.CompositionSaleStrategy;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.Optional;
import java.util.Set;

public class CompositionSaleDto implements SaleStrategy {

    @Getter
    private final String description;

    @Getter
    private final double percentage;

    @Getter
    private final Set<String> shouldHave;

    @Getter
    private final Set<String> shouldNotHave;

    @ConstructorProperties({"description", "percentage", "shouldHave", "shouldNotHave"})
    public CompositionSaleDto(String description, double percentage, Set<String> shouldHave, Set<String> shouldNotHave) {
        this.description = description;
        this.percentage = percentage;
        this.shouldHave = shouldHave;
        this.shouldNotHave = shouldNotHave;
    }

    @Override
    public Optional<Discount> apply(OrderContext context, OrderItem order) {
        return new CompositionSaleStrategy(description, percentage, shouldHave, shouldNotHave).apply(context, order);
    }
}
