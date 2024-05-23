package com.example.compose.feature_search.domain.usecases

import com.example.compose.feature_search.domain.models.SearchFilters
import com.example.end_to_end_app.common.domain.model.animal.details.Age
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import java.util.Locale
import javax.inject.Inject

class GetSearchFilters @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    companion object{
        const val NO_FILTER_SELECTED = "Any"
    }

    suspend operator fun invoke(): SearchFilters {
        val types = listOf(NO_FILTER_SELECTED) + animalRepository.getAnimalTypes()

        val ages = animalRepository.getAnimalAges().map { age ->
            if (age.name == Age.UNKNOWN.name) {
                NO_FILTER_SELECTED
            } else {
                age.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            }
        }

        return SearchFilters(ages, types)
    }
}