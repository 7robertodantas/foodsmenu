package com.food.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.core.facade.Element;
import com.food.core.facade.Item;
import com.food.core.facade.ItemsContext;
import com.food.core.sales.SaleStrategy;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Data
@NoArgsConstructor
public class MenuDescriptionDto implements ItemsContext {

    @JsonProperty("id")
    private String id;

    @JsonProperty("items")
    private List<ItemDto> itemsDto;

    @JsonProperty("sales")
    private SalesDto salesDto;

    @JsonProperty("ingredients")
    private List<IngredientDto> ingredientsDto;

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

        @JsonIgnore
        public Set<SaleStrategy> getStrategies(){
            return Stream.concat(getComposition().stream(), getQuantity().stream()).collect(toSet());
        }

    }

    @JsonIgnore
    @Transient
    public Set<SaleStrategy> getStrategies(){
        return salesDto.getStrategies();
    }

    @JsonIgnore
    @Transient
    public List<Item> getItems() {
        return new ArrayList<>(itemsDto);
    }

    @JsonIgnore
    @Transient
    public Set<Element> getElements() {
        return new HashSet<>(ingredientsDto);
    }

}
