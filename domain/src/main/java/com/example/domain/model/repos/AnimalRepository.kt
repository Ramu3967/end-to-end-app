package com.example.domain.model.repos

import com.example.domain.model.animal.Animal
import com.example.domain.model.animal.details.Age
import com.example.domain.model.animal.details.AnimalWithDetails
import com.example.domain.model.pagination.PaginatedAnimals
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    suspend fun getAnimals(): Flow<List<Animal>>

    suspend fun saveAnimals(animals: List<AnimalWithDetails>)

    suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals

    suspend fun getAnimalTypes(): List<String>

    fun getAnimalAges(): List<Age>

    suspend fun searchCachedAnimalsWith(input: String, age: String, type: String): Flow<List<Animal>>

    suspend fun requestAnimalsRemotely(
        name: String, age: String, type: String,
        pageToLoad: Int,
        pageSize: Int
    ): PaginatedAnimals

}