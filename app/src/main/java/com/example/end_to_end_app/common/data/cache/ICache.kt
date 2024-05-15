package com.example.end_to_end_app.common.data.cache

import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.end_to_end_app.common.data.cache.model.cachedorganization.CachedOrganization
import kotlinx.coroutines.flow.Flow

interface ICache {
    suspend fun saveOrganizations(organizations: List<CachedOrganization>)

    suspend fun saveNearbyAnimals(animals: List<CachedAnimalAggregate>)

    suspend fun getNearbyAnimals(): Flow<List<CachedAnimalAggregate>>
}