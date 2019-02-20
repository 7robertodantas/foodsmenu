package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Element;
import com.food.core.facade.Item;
import com.food.core.facade.ItemsContext;
import com.food.core.sales.SaleStrategy;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class MenuDescriptionDto implements ItemsContext {

    @Getter
    @JsonProperty("id")
    private final String id;

    @Getter
    @JsonProperty("items")
    private final List<ItemDto> itemsDto;

    @Getter
    @JsonProperty("sales")
    private final SalesDto salesDto;

    @Getter
    @JsonProperty("ingredients")
    private final List<IngredientDto> ingredientsDto;

    @ConstructorProperties({"id", "items", "sales", "ingredients"})
    public MenuDescriptionDto(String id, List<ItemDto> items, SalesDto sales, List<IngredientDto> ingredients) {
        this.id = id;
        this.itemsDto = items;
        this.salesDto = sales;
        this.ingredientsDto = ingredients;
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
    public Set<SaleStrategy> getStrategies(){
        return Stream.concat(salesDto.getComposition().stream(), salesDto.getQuantity().stream()).collect(toSet());
    }

    @JsonIgnore
    public List<Item> getItems() {
        return new ArrayList<>(itemsDto);
    }

    @JsonIgnore
    public Set<Element> getElements() {
        return new HashSet<>(ingredientsDto);
    }

}
