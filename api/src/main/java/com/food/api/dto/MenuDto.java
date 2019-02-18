package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Menu;
import com.food.core.facade.MenuItem;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MenuDto implements Menu {

    @Getter
    @JsonProperty("id")
    private final String id;

    @Getter
    @JsonProperty("items")
    private final List<MenuItemDto> itemsDto;

    @Getter
    @JsonProperty("sales")
    private final SalesDto salesDto;

    @ConstructorProperties({"id", "items", "sales"})
    public MenuDto(String id, List<MenuItemDto> items, SalesDto sales) {
        this.id = id;
        this.itemsDto = items;
        this.salesDto = sales;
    }

    public static class SalesDto {

        @Getter
        @JsonProperty("quantity")
        private final List<QuantitySaleDto> quantity;

        @Getter
        @JsonProperty("composition")
        private final List<CompositionSaleDto> composition;

        @ConstructorProperties({"quantity", "composition"})
        public SalesDto(List<QuantitySaleDto> quantity, List<CompositionSaleDto> composition) {
            this.quantity = quantity;
            this.composition = composition;
        }
    }

    @JsonIgnore
    public List<SaleStrategy> getSales(){
        return Stream.concat(salesDto.getComposition().stream(), salesDto.getQuantity().stream()).collect(toList());
    }

    @JsonIgnore
    public List<MenuItem> getItems() {
        return new ArrayList<>(itemsDto);
    }

}
