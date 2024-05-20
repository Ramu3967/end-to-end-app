package com.example.end_to_end_app.feature_search.presentation

import com.example.end_to_end_app.common.presentation.model.UIAnimal

data class SearchAnimalState(
    val loading: Boolean = true,
    val dataAnimals: List<UIAnimal> = emptyList(),
    val ageFilterValues: List<String> = emptyList(),
    val typeFilterValues: List<String> = emptyList(),
    val failure: Throwable? = null
)