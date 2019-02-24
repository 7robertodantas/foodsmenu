package com.food.api.handler;

import com.food.api.dto.*;
import com.food.api.integration.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static com.food.core.utils.CollectionUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class MenuDescriptionHandlerTestIT extends IntegrationTestSupport {

    @Test
    @DisplayName("Should create menu post - POST /menu/descriptions")
    public void shouldCreateMenuDescription() {
        List<ItemDto> itemDtos = singletonList(new ItemDto("X-Bacon", asList("Bacon", "Hamburguer", "Cheese"),
                asSet("10off", "hamburguer3pay2", "cheese3pay2")));

        List<QuantitySaleDto> quantitySaleDtos = asList(new QuantitySaleDto("hamburguer3pay2", "For each 3 hamburguers pay only 2.", "Hamburguer", 3, 1),
                new QuantitySaleDto("cheese3pay2", "For each 3 cheese pay only 2.", "Cheese", 3, 1));

        List<CompositionSaleDto> compositionSaleDtos = singletonList(
                new CompositionSaleDto("10off", "10% OFF - Must have lettuce and not have bacon.", 0.1,
                        asSet("Lettuce"),
                        asSet("Bacon")));

        MenuDescriptionDto.SalesDto salesDto = new MenuDescriptionDto.SalesDto(quantitySaleDtos, compositionSaleDtos);

        List<IngredientDto> ingredientDtos = asList(new IngredientDto("Lettuce", 0.4),
                new IngredientDto("Bacon", 2.0),
                new IngredientDto("Hamburguer", 3.0),
                new IngredientDto("Egg", 0.8),
                new IngredientDto("Cheese", 1.5));

        MenuDescriptionDto menuDescription = new MenuDescriptionDto(null, itemDtos, salesDto, ingredientDtos);

        web.post().uri("/menus/descriptions")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromObject(menuDescription))
                .exchange()
                .expectStatus().isOk();
    }


}
