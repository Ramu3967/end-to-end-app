package com.example.end_to_end_app.common.data

import com.example.end_to_end_app.common.data.api.IPetFinderApi
import com.example.end_to_end_app.common.data.api.model.mappers.ApiAnimalMapper
import com.example.end_to_end_app.common.data.cache.ICache
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.end_to_end_app.common.data.cache.model.cachedorganization.CachedOrganization
import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.domain.model.animal.details.AnimalWithDetails
import com.example.end_to_end_app.common.domain.model.pagination.PaginatedAnimals
import com.example.end_to_end_app.common.domain.model.pagination.Pagination
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This repo is a contract between data and domain layers. Use-cases use these methods.
 */
class PetFinderAnimalRepository @Inject constructor(
    private val api:IPetFinderApi,
    private val cache: ICache,
    private val apiAnimalMapper: ApiAnimalMapper
): AnimalRepository {

    override suspend fun getAnimals(): Flow<List<Animal>> {
        /*
        Now, the data should be retrieved from the cache
        try{
            val (apiAnimals,_) = api.getNearbyAnimals()
            val animals = apiAnimals?.map {
                apiAnimalMapper.mapToDomain(it)
            }.orEmpty()
            val result = animals.map {
                Animal(id = it.id, name = it.name, type = it.type, media = it.media, tags = it.tags,
                    adoptionStatus = it.adoptionStatus, publishedAt = it.publishedAt)
            }
            emit(result)
        }catch (e:Exception){
            Log.e("PetFinderAnimalRepository", "Error fetching animals: ${e.message}", e)
            emit(emptyList())
        }*/

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

        val result = animals.map {
            Animal(id = it.id, name = it.name, type = it.type, media = it.media, tags = it.tags,
                adoptionStatus = it.adoptionStatus, publishedAt = it.publishedAt)
        }
        return PaginatedAnimals(animals, Pagination(9,9))
    }


}