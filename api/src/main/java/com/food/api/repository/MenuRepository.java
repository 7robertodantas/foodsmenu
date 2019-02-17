package com.food.api.repository;

import com.food.core.model.Menu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MenuRepository extends ReactiveCrudRepository<Menu, String> {
}
