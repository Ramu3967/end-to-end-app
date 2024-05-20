package com.example.end_to_end_app.feature_search.domain.usecases

import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
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
    ){
        val combinedFlow = combine(
            // no spaces, and the min len > 2
            textInput.map { it.trim() }. filter { it.length > 2 },
            ageInput.replaceUIEmptyValue(),
            typeInput.replaceUIEmptyValue()
        ){ input, age, type ->
            animalRepository.searchCachedAnimalsWith(input, age, type)
        }
    }

    // replace the "Any" with empty string for a broader api search
    private fun Flow<String>.replaceUIEmptyValue(): Flow<String> =
        this.map { if(it == GetSearchFilters.NO_FILTER_SELECTED) "" else it }
}