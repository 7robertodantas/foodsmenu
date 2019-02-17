package com.food.api.handler;

import com.food.api.dto.IngredientDto;
import com.food.api.repository.IngredientRepository;
import com.food.core.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class IngredientHandler {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientHandler(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Mono<ServerResponse> getIngredients(ServerRequest request) {
        return ingredientRepository.findAll()
                .map(this::toDto)
                .collectList()
                .flatMap(data -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> putIngredient(ServerRequest request) {
        return request.bodyToMono(IngredientDto.class)
                .map(this::fromDto)
                .flatMap(ingredientRepository::save)
                .flatMap(saved -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> postIngredient(ServerRequest request) {
        return request.bodyToMono(IngredientDto.class)
                .map(this::fromDto)
                .flatMap(ingredientRepository::save)
                .flatMap(saved -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> deleteIngredient(ServerRequest request) {
        String id = request.pathVariable("id");
        return ingredientRepository.findById(id)
                .flatMap(ingredientRepository::delete)
                .flatMap((none) -> noContent().build());
    }

    private Ingredient fromDto(IngredientDto dto) {
        return new Ingredient(dto.getName(), dto.getValue());
    }

    private IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getName(), ingredient.getValue());
    }


}
