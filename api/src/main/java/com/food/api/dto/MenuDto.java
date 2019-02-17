package com.food.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
public class MenuDto {

    @Getter
    private final List<ItemDto> items;

    @Getter
    private final SalesDto sales;

    @AllArgsConstructor
    public static class SalesDto {

        @Getter
        private final List<QuantitySaleDto> quantity;

        @Getter
        private final List<CompositionSaleDto> composition;

    }

}
