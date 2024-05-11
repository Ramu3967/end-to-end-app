package com.example.end_to_end_app.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiAnimal(
 @SerializedName("id") val id: Long?,
 @SerializedName("organization_id") val organizationId: String?,
 @SerializedName("url") val url: String?,
 @SerializedName("type") val type: String?,
 @SerializedName("species") val species:String?,
 @SerializedName("breeds") val breeds: ApiBreeds?,
 @SerializedName("colors") val colors: ApiColors?,
 @SerializedName("age") val age: String?,
 @SerializedName("gender") val gender: String?,
 @SerializedName("size") val size: String?,
 @SerializedName("coat") val coat: String?,
 @SerializedName("name") val name: String?,
 @SerializedName("description") val description: String?,
 @SerializedName("photos") val photos: List<ApiPhotoSizes>?,
 @SerializedName("videos") val videos: List<ApiVideoLink>?,
 @SerializedName("status") val status: String?,
 @SerializedName("attributes") val attributes: ApiAttributes?,
 @SerializedName("environment") val environment: ApiEnvironment?,
 @SerializedName("tags") val tags: List<String?>?,
 @SerializedName("contact") val contact: ApiContact?,
 @SerializedName("published_at") val publishedAt: String?,
 @SerializedName("distance") val distance: Float?
)

data class ApiBreeds(
 @SerializedName("primary") val primary: String?,
 @SerializedName("secondary") val secondary: String?,
 @SerializedName("mixed") val mixed: Boolean?,
 @SerializedName("unknown") val unknown: Boolean?
)

data class ApiColors(
 @SerializedName("primary") val primary: String?,
 @SerializedName("secondary") val secondary: String?,
 @SerializedName("tertiary") val tertiary: String?
)

data class ApiPhotoSizes(
 @SerializedName("small") val small: String?,
 @SerializedName("medium") val medium: String?,
 @SerializedName("large") val large: String?,
 @SerializedName("full") val full: String?
)

data class ApiVideoLink(
 @SerializedName("embed") val embed: String?
)

data class ApiAttributes(
 @SerializedName("spayed_neutered") val spayedNeutered: Boolean?,
 @SerializedName("house_trained") val houseTrained: Boolean?,
 @SerializedName("declawed") val declawed: Boolean?,
 @SerializedName("special_needs") val specialNeeds: Boolean?,
 @SerializedName("shots_current") val shotsCurrent: Boolean?
)

data class ApiEnvironment(
 @SerializedName("children") val children: Boolean?,
 @SerializedName("dogs") val dogs: Boolean?,
 @SerializedName("cats") val cats: Boolean?
)

data class ApiContact(
 @SerializedName("email") val email: String?,
 @SerializedName("phone") val phone: String?,
 @SerializedName("address") val address: ApiAddress?
)

data class ApiAddress(
 @SerializedName("address1") val address1: String?,
 @SerializedName("address2") val address2: String?,
 @SerializedName("city") val city: String?,
 @SerializedName("state") val state: String?,
 @SerializedName("postcode") val postcode: String?,
 @SerializedName("country") val country: String?
)

