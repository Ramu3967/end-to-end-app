package com.example.end_to_end_app.common.domain.model.repos

import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.domain.model.animal.details.AnimalWithDetails
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    suspend fun getAnimals(): Flow<List<Animal>>

    suspend fun saveAnimals(animals: List<AnimalWithDetails>)
}