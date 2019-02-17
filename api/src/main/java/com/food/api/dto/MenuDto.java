package com.food.api.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

public class MenuDto {

    @Getter
    private final List<ItemDto> items;

    @Getter
    private final SalesDto sales;

    @ConstructorProperties({"items", "sales"})
    public MenuDto(List<ItemDto> items, SalesDto sales) {
        this.items = items;
        this.sales = sales;
    }

    public static class SalesDto {

        @Getter
        private final List<QuantitySaleDto> quantity;

        @Getter
        private final List<CompositionSaleDto> composition;

        @ConstructorProperties({"quantity", "composition"})
        public SalesDto(List<QuantitySaleDto> quantity, List<CompositionSaleDto> composition) {
            this.quantity = quantity;
            this.composition = composition;
        }
    }

}