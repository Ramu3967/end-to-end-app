package com.example.end_to_end_app.feature_search.domain.usecases

import com.example.end_to_end_app.common.domain.NoMoreAnimalsException
import com.example.end_to_end_app.common.domain.model.pagination.Pagination
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import com.example.end_to_end_app.feature_search.domain.models.SearchParameters
import javax.inject.Inject

class SearchAnimalsRemotely @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(
        searchParameters: SearchParameters,
        pageToLoad: Int,
        pageSize: Int
    ): Pagination{
        val (name, age, type) = searchParameters
        val (animals, pagination) = animalRepository.requestAnimalsRemotely(
            name, age, type, pageToLoad, pageSize)

        if(animals.isEmpty()) throw NoMoreAnimalsException("No more remote animals!!")
        else{
            animalRepository.saveAnimals(animals)
            return pagination
        }
    }
}