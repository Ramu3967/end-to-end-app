package com.example.data

import com.example.data.api.IPetFinderApi
import com.example.data.api.model.mappers.ApiAnimalMapper
import com.example.data.api.model.mappers.ApiPaginationMapper
import com.example.data.cache.ICache
import com.example.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.data.cache.model.cachedorganization.CachedOrganization
import com.example.domain.model.animal.Animal
import com.example.domain.model.animal.details.Age
import com.example.domain.model.animal.details.AnimalWithDetails
import com.example.domain.model.pagination.PaginatedAnimals
import com.example.domain.model.pagination.Pagination
import com.example.domain.model.repos.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This repo is a contract between data and domain layers. Use-cases use these methods.
 */
class PetFinderAnimalRepository @Inject constructor(
    private val api: IPetFinderApi,
    private val cache: ICache,
    private val apiAnimalMapper: ApiAnimalMapper,
    private val apiPaginationMapper: ApiPaginationMapper
): AnimalRepository {

    override suspend fun getAnimals(): Flow<List<Animal>> {
        val result = cache.getNearbyAnimals()
        .map { animAg ->
            animAg.map {
                it.animal.toAnimalDomain(it.photos,it.videos,it.tags)
            }
        }
        return result
    }

    override suspend fun saveAnimals(animals: List<AnimalWithDetails>) {
        val orgs = animals.map {  CachedOrganization.fromDomain(it.details.organization) }
        cache.saveOrganizations(orgs)

        val cachedAnimals = animals.map {
            CachedAnimalAggregate.fromDomain(it)
        }
        cache.saveNearbyAnimals(cachedAnimals)
    }

    override suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals {
        val (apiAnimals,_) = api.getNearbyAnimals(
            pageToLoad = pageToLoad,
            pageSize = numberOfItems
        )
        val animals = apiAnimals?.map {
            apiAnimalMapper.mapToDomain(it)
        }.orEmpty()

        saveAnimals(animals)

        return PaginatedAnimals(animals, Pagination(pageToLoad,numberOfItems))
    }

    override suspend fun getAnimalTypes(): List<String> {
        return cache.getAllTypes()
    }

    override fun getAnimalAges(): List<Age> {
        return Age.entries
    }

    override suspend fun searchCachedAnimalsWith(
        input: String,
        age: String,
        type: String
    ): Flow<List<Animal>> = cache.searchAnimalsWith(input,age, type).map {cachedAnimals ->
        cachedAnimals.map {
            it.animal.toAnimalDomain(
                it.photos, it.videos, it.tags
            )
        }
    }

    override suspend fun requestAnimalsRemotely(
        name: String,
        age: String,
        type: String,
        pageToLoad: Int,
        pageSize: Int
    ): PaginatedAnimals {
        val (apiAnimals, apiPagination) = api.getAnimalsWithParameters(name, age, type, pageToLoad, pageSize)
        val animals = apiAnimals?.map{apiAnimalMapper.mapToDomain(it)}.orEmpty()
        val pagination = apiPaginationMapper.mapToDomain(apiPagination)
        return PaginatedAnimals(animals, pagination)
    }


}