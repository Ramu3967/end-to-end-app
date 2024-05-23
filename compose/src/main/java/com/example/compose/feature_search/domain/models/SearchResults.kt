package com.example.compose.feature_search.domain.models

import com.example.compose.feature_search.domain.models.SearchParameters
import com.example.end_to_end_app.common.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)
