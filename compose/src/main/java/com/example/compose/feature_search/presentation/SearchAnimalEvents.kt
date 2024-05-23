package com.example.compose.feature_search.presentation

sealed class SearchAnimalEvents {
    object PrepareForSearchEvent: SearchAnimalEvents()
    data class QueryInput(val input: String): SearchAnimalEvents()
    data class AgeValueSelected(val age: String): SearchAnimalEvents()
    data class TypeValueSelected(val type: String): SearchAnimalEvents()
}