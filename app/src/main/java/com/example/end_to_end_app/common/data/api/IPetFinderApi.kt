package com.example.end_to_end_app.common.data.api

import com.example.end_to_end_app.common.data.api.model.ApiPaginatedAnimals
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
}