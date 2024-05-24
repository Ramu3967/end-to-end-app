package com.example.pf_utils.features.search.presentation

sealed class SearchAnimalEvents {
    object PrepareForSearchEvent: SearchAnimalEvents()
    data class QueryInput(val input: String): SearchAnimalEvents()
    data class AgeValueSelected(val age: String): SearchAnimalEvents()
    data class TypeValueSelected(val type: String): SearchAnimalEvents()
}