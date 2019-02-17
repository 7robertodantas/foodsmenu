package com.food.api.repository;

import com.food.api.dto.IngredientDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IngredientRepository extends ReactiveCrudRepository<IngredientDto, String> {
}
