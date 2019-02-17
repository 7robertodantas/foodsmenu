package com.food.api.handler;

import com.food.api.dto.IngredientDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.util.Collections.emptyList;

@Component
public class IngredientHandler {

    public Mono<ServerResponse> getIngredients(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(emptyList()));
    }

    public Mono<ServerResponse> putIngredient(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(IngredientDto.builder().build()));
    }

    public Mono<ServerResponse> postIngredient(ServerRequest request) {
        return ServerResponse.created(URI.create(""))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(IngredientDto.builder().build()));
    }

    public Mono<ServerResponse> deleteIngredient(ServerRequest request) {
        return ServerResponse.noContent().build();
    }


}
