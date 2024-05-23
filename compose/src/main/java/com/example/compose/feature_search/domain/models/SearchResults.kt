package com.example.compose.feature_search.domain.models

import com.example.domain.model.animal.Animal


data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)
