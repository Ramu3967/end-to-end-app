package com.example.end_to_end_app.feature_search.domain.models

import com.example.end_to_end_app.common.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)
