package com.food.api.router;

import com.food.api.handler.IngredientHandler;
import com.food.api.handler.MenuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> menuRouter(MenuHandler menuHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/menus"), menuHandler::getMenus);
    }

    @Bean
    public RouterFunction<ServerResponse> ingredientRouter(IngredientHandler ingredientHandler) {
        return RouterFunctions.route()
                .GET("/ingredients", accept(APPLICATION_JSON), ingredientHandler::getIngredients)
                .PUT("/ingredients/{name}", accept(APPLICATION_JSON), ingredientHandler::putIngredient)
                .DELETE("/ingredients/{name}", accept(APPLICATION_JSON), ingredientHandler::deleteIngredient)
                .POST("/ingredients", accept(APPLICATION_JSON), ingredientHandler::postIngredient)
                .build();
    }

}
