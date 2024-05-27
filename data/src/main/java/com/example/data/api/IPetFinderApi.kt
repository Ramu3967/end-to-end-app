package com.example.data.api

import com.example.data.api.model.ApiPaginatedAnimals
import retrofit2.http.GET
import retrofit2.http.Query

interface IPetFinderApi {
    @GET(ApiConstants.ANIMALS_ENDPOINT)
    suspend fun getNearbyAnimals(
        @Query(ApiParameters.PAGE) pageToLoad: Int = 1,
        @Query(ApiParameters.LIMIT) pageSize: Int = 10,
        @Query(ApiParameters.LOCATION) postcode: String = "07097",
        @Query(ApiParameters.DISTANCE) maxDistance: Int = 100
    ): ApiPaginatedAnimals

    @GET(ApiConstants.ANIMALS_ENDPOINT)
    suspend fun getAnimalsWithParameters(
        @Query(ApiParameters.NAME) name: String,
        @Query(ApiParameters.AGE) age: String,
        @Query(ApiParameters.TYPE) type: String,
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int
    ): ApiPaginatedAnimals
}