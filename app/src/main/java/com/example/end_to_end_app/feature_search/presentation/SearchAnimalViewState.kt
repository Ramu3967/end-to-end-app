package com.example.end_to_end_app.feature_search.presentation

import com.example.end_to_end_app.common.presentation.model.UIAnimal

data class SearchAnimalViewState(
    val noSearchQuery: Boolean = true,
    val searchResults: List<UIAnimal> = emptyList(), // could be local or remote
    val ageFilterValues: List<String> = emptyList(),
    val typeFilterValues: List<String> = emptyList(),
    val searchingRemotely: Boolean = false,
    val noRemoteResults: Boolean = false,
    val failure: Throwable? = null,
){
    // these util functions facilitate updating view states

    fun updateToHasFailure(throwable: Throwable): SearchAnimalViewState {
        return copy(failure = throwable)
    }

    fun updateToSearchingRemotely(): SearchAnimalViewState {
        return copy(searchingRemotely = true, searchResults = emptyList())
    }

    fun updateToSearching(): SearchAnimalViewState {
        return copy(
            noSearchQuery = false,
            searchingRemotely = true,
            noRemoteResults = false
        )
    }

    // initial state
    fun updateToNoSearchQuery(): SearchAnimalViewState {
        return copy(
            searchResults = emptyList(),
            noSearchQuery = true,
            noRemoteResults = false,
            searchingRemotely = false
        )
    }

    fun updateToHasSearchedResults(animals: List<UIAnimal>): SearchAnimalViewState {
        return copy(
            noSearchQuery = false,
            searchResults = animals,
            searchingRemotely = false,
            noRemoteResults = false
        )
    }

    fun updateToNoResultsAvailable(): SearchAnimalViewState {
        return copy(searchingRemotely = false, noRemoteResults = true)
    }


}