package com.food.api.handler;

import com.food.api.dto.MenuDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static java.util.Collections.emptyList;

@Component
public class MenuHandler {

    public Mono<ServerResponse> getMenu(ServerRequest request) {
        MenuDto menu = new MenuDto(emptyList(), new MenuDto.SalesDto(emptyList(), emptyList()));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(menu));
    }

}
