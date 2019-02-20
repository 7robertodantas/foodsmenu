package com.food.api.handler;

import com.food.api.dto.MenuDescriptionDto;
import com.food.api.repository.MenuDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class MenuDescriptionHandler {

    private final MenuDescriptionRepository menuDescriptionRepository;

    @Autowired
    public MenuDescriptionHandler(MenuDescriptionRepository menuDescriptionRepository) {
        this.menuDescriptionRepository = menuDescriptionRepository;
    }

    public Mono<ServerResponse> getMenuDescriptions(ServerRequest request) {
        return menuDescriptionRepository.findAll()
                .collectList()
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> getMenuDescription(ServerRequest request) {
        String id = request.pathVariable("id");
        return menuDescriptionRepository.findById(id)
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> putMenuDescription(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(MenuDescriptionDto.class)
                .map(menu -> new MenuDescriptionDto(id, menu.getItemsDto(), menu.getSalesDto(), menu.getIngredientsDto()))
                .flatMap(menuDescriptionRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> postMenuDescription(ServerRequest request) {
        return request.bodyToMono(MenuDescriptionDto.class)
                .flatMap(menuDescriptionRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

}
