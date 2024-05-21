package com.example.end_to_end_app.feature_search.presentation

import com.example.end_to_end_app.common.presentation.model.UIAnimal

data class SearchAnimalViewState(
    val loading: Boolean = true,
    val searchResults: List<UIAnimal> = emptyList(), // could be local or remote
    val ageFilterValues: List<String> = emptyList(),
    val typeFilterValues: List<String> = emptyList(),
    val failure: Throwable? = null,
    val searchingRemotely: Boolean = false,
    val noRemoteResults: Boolean = false,
){
    // these util functions facilitate updating view states

    fun updateToHasFailure(throwable: Throwable): SearchAnimalViewState {
        return copy(failure = throwable)
    }

    fun updateToSearchingRemotely(): SearchAnimalViewState {
        return copy(searchingRemotely = true, searchResults = emptyList())
    }


}