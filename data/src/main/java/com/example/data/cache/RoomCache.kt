package com.example.data.cache
import com.example.data.cache.daos.DaoAnimals
import com.example.end_to_end_app.common.data.cache.daos.DaoOrganization
import com.example.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.data.cache.model.cachedorganization.CachedOrganization
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val daoAnimals: DaoAnimals,
    private val daoOrganization: DaoOrganization
): ICache {
    override suspend fun saveOrganizations(organizations: List<CachedOrganization>) {
        daoOrganization.insert(organizations)
    }

    override suspend fun saveNearbyAnimals(animals: List<CachedAnimalAggregate>) {
        daoAnimals.insertAnimalsWithDetails(animals)
    }

    override suspend fun getNearbyAnimals(): Flow<List<CachedAnimalAggregate>> {
        return daoAnimals.getAllAnimals()
    }

    override suspend fun getAllTypes(): List<String> {
        return daoAnimals.getAllTypes()
    }

    override suspend fun searchAnimalsWith(
        input: String,
        age: String,
        type: String
    ): Flow<List<CachedAnimalAggregate>> {
        return daoAnimals.searchAnimalsWith(input, age, type)
    }
}