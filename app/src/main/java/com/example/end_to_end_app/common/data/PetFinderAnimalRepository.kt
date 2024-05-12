package com.example.end_to_end_app.common.data

import android.util.Log
import com.example.end_to_end_app.common.data.api.PetFinderApi
import com.example.end_to_end_app.common.data.api.model.mappers.ApiAnimalMapper
import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This repo is a contract between data and domain layers
 */
class PetFinderAnimalRepository @Inject constructor(
    private val api:PetFinderApi,
    private val apiAnimalMapper: ApiAnimalMapper
): AnimalRepository {
    override suspend fun getAnimals(): Flow<List<Animal>> = flow{

        try{
            val (apiAnimals,_) = api.getNearbyAnimals()
            val animals = apiAnimals?.map {
                apiAnimalMapper.mapToDomain(it)
            }.orEmpty()

            emit(
                animals.map {
                    Animal(id = it.id, name = it.name, type = it.type, media = it.media, tags = it.tags,
                        adoptionStatus = it.adoptionStatus, publishedAt = it.publishedAt)
                })
        }catch (e:Exception){
            Log.e("PetFinderAnimalRepository", "Error fetching animals: ${e.message}", e)
            emit(emptyList())
        }


    }
}