package com.food.api.handler;

import com.food.api.dto.MenuDto;
import com.food.api.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
                .collectList()
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> getMenu(ServerRequest request) {
        String id = request.pathVariable("id");
        return menuRepository.findById(id)
                .flatMap(data -> ok()
                        .body(fromObject(data)));
    }

    public Mono<ServerResponse> putMenu(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(MenuDto.class)
                .map(menu -> new MenuDto(id, menu.getItemsDto(), menu.getSalesDto()))
                .flatMap(menuRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

    public Mono<ServerResponse> postMenu(ServerRequest request) {
        return request.bodyToMono(MenuDto.class)
                .flatMap(menuRepository::save)
                .flatMap(saved -> ok()
                        .body(fromObject(saved)));
    }

}
