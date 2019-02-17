package com.food.api.repository;

import com.food.api.dto.MenuDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MenuRepository extends ReactiveCrudRepository<MenuDto, String> {
}
