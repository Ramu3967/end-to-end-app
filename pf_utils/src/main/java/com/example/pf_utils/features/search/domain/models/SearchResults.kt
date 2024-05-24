package com.example.pf_utils.features.search.domain.models

import com.example.domain.model.animal.Animal


data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)
