package com.example.end_to_end_app.animalsnearyou.domain.usecases

import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import javax.inject.Inject

/**
 * Views trigger the events and View-Model in turn uses the following use-case and returns the ViewState
 * so that views can observe/consume the state.
 */
class GetAnimals @Inject constructor(
    private val animalRepo: AnimalRepository
){
    // felt better to call it GetAnimals() than getAnimals.makeApiCall()
    suspend operator fun invoke() = animalRepo.getAnimals()
}