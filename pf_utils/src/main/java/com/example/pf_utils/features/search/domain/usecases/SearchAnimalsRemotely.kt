package com.example.pf_utils.features.search.domain.usecases

import com.example.domain.NoMoreAnimalsException
import com.example.domain.model.pagination.Pagination
import com.example.domain.model.repos.AnimalRepository
import com.example.pf_utils.features.search.domain.models.SearchParameters
import javax.inject.Inject

class SearchAnimalsRemotely @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(
        searchParameters: SearchParameters,
        pageToLoad: Int,
        pageSize: Int
    ): Pagination {
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