package com.example.domain.model.pagination

import com.example.domain.model.animal.details.AnimalWithDetails


data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)
