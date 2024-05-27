package com.example.pf_utils.features.animalsnearyou.domain.usecases

import com.example.domain.NoMoreAnimalsException
import com.example.domain.model.pagination.Pagination
import com.example.domain.model.repos.AnimalRepository
import javax.inject.Inject

class RequestNextPageOfAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (animals,pagination) = animalRepository
            .requestMoreAnimals(pageToLoad = pageToLoad, numberOfItems = pageSize )
        if(animals.isEmpty())
            throw NoMoreAnimalsException("No animals Nearby :(")
        // emits into the flow and updates the UI
        animalRepository.saveAnimals(animals)
        return pagination
    }
}