package com.food.core.model;

import com.food.core.facade.Discount;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(of = {"description", "value"})
@ToString
public class DiscountImpl implements Discount {

    @Getter
    private final String description;

    @Getter
    private final double value;
}
