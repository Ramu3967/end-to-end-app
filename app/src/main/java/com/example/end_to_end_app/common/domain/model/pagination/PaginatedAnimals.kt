package com.example.end_to_end_app.common.domain.model.pagination

import com.example.end_to_end_app.common.domain.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)
