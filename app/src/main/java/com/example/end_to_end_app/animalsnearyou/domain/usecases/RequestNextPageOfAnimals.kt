package com.example.end_to_end_app.animalsnearyou.domain.usecases

import com.example.end_to_end_app.common.domain.NoMoreAnimalsException
import com.example.end_to_end_app.common.domain.model.pagination.Pagination
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import javax.inject.Inject

class RequestNextPageOfAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ){
        val (animals,pagination) = animalRepository
            .requestMoreAnimals(pageToLoad = pageToLoad, numberOfItems = pageSize )
        if(animals.isEmpty())
            throw NoMoreAnimalsException("No animals Nearby :(")
        // emits into the flow and updates the UI
        animalRepository.saveAnimals(animals)
    }
}