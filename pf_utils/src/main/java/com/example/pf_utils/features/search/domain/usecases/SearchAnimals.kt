package com.example.pf_utils.features.search.domain.usecases

import com.example.domain.model.repos.AnimalRepository
import com.example.pf_utils.features.search.domain.models.SearchParameters
import com.example.pf_utils.features.search.domain.models.SearchResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * triggers a local search if any of input values (text, spinners) changes
 */
class SearchAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
){
    operator fun invoke(
        textInput: Flow<String>,
        ageInput: Flow<String>,
        typeInput: Flow<String>
    ): Flow<SearchResults> {
        val combinedFlow = combine(
            // no spaces, and the min len >= 2
            textInput.debounce(1500L).map { it.trim() }. filter { it.length >= 2 },
            ageInput.replaceUIEmptyValue(),
            typeInput.replaceUIEmptyValue()
        ){ input, age, type ->
            animalRepository.searchCachedAnimalsWith(input, age, type).map {
                SearchResults(it, SearchParameters(input, age, type))
            }
        }
        return combinedFlow.flatMapLatest { it }
    }

    // replace the "Any" with empty string for a broader api search
    private fun Flow<String>.replaceUIEmptyValue(): Flow<String> =
        this.map { if(it == GetSearchFilters.NO_FILTER_SELECTED) "" else it }
}