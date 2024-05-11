package com.example.end_to_end_app.data.api

import com.example.end_to_end_app.data.api.model.ApiPaginatedAnimals
import retrofit2.http.GET

interface PetFinderApi {
    @GET(ApiConstants.ANIMALS_ENDPOINT)
    suspend fun getNearbyAnimals(/*add query params here*/): ApiPaginatedAnimals
}