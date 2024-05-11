package com.example.end_to_end_app.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * please checkout the api response at - https://www.petfinder.com/developers/v2/docs/
 */
data class ApiPaginatedAnimals(
    @SerializedName("animals") val animals: List<ApiAnimal>?,
    @SerializedName("pagination") val pagination: ApiPagination?
)

data class ApiPagination(
    @SerializedName("count_per_page") val countPerPage: Int?,
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("current_page") val currentPage: Int?,
    @SerializedName("total_pages") val totalPages: Int?
)
