package com.food.api.handler;

import com.food.api.dto.ItemDto;
import com.food.api.dto.MenuDto;
import com.food.api.repository.MenuRepository;
import com.food.core.model.Ingredient;
import com.food.core.model.Menu;
import com.food.core.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class MenuHandler {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Mono<ServerResponse> getMenus(ServerRequest request) {
        return menuRepository.findAll()
                .map(this::toDto)
                .collectList()
                .flatMap(data -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(data)));
    }

    private MenuDto toDto(Menu menu) {
        return new MenuDto(toDto(menu.getItems()), new MenuDto.SalesDto(emptyList(), emptyList()));
    }

    private List<ItemDto> toDto(List<MenuItem> menuItems){
        return menuItems.stream().map(this::toDto).collect(toList());
    }

    private ItemDto toDto(MenuItem menuItem){
        return new ItemDto(menuItem.getName(), toIngredientsString(menuItem.getIngredients()), menuItem.getNetPrice());
    }

    private List<String> toIngredientsString(List<Ingredient> ingredients) {
        return ingredients.stream().map(Ingredient::getName).collect(toList());
    }



}
