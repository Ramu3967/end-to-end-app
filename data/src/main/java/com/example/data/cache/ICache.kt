package com.example.data.cache

import com.example.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.data.cache.model.cachedorganization.CachedOrganization
import kotlinx.coroutines.flow.Flow

/**
 * An abstraction layer for accessing db. Use Room(incorporated), Sqlite etc to implement it.
 */
interface ICache {
    suspend fun saveOrganizations(organizations: List<CachedOrganization>)

    suspend fun saveNearbyAnimals(animals: List<CachedAnimalAggregate>)

    suspend fun getNearbyAnimals(): Flow<List<CachedAnimalAggregate>>

    suspend fun getAllTypes(): List<String>

    suspend fun searchAnimalsWith(input: String, age: String, type: String): Flow<List<CachedAnimalAggregate>>
}