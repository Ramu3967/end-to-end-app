package com.example.end_to_end_app.common.data.cache.model.cachedanimal

import androidx.room.Entity
import com.example.end_to_end_app.common.domain.model.animal.AdoptionStatus
import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.domain.model.animal.Media
import com.example.end_to_end_app.common.domain.model.animal.details.AnimalWithDetails
import com.example.end_to_end_app.common.utils.DateTimeUtils

/**
 * This table/entity is dependent on the CachedOrg table. It has one to many relationship (1 Org
 *  can have multiple animals)
 */

@Entity(
    tableName = "animals"
)
data class CachedAnimalWithDetails(
    val animalId: Long,
    val organizationId: String,
    val name: String,
    val type: String,
    val species: String,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String,
    val description: String,
    val primaryBreed: String,
    val secondaryBreed: String,
    val primaryColor: String,
    val secondaryColor: String,
    val tertiaryColor: String,
    val adoptionStatus: String,
    val isSpayedNeutered: Boolean,
    val isDeclawed: Boolean,
    val hasSpecialNeeds: Boolean,
    val areShotsCurrent: Boolean,
    val isGoodWithChildren: Boolean,
    val isGoodWithDogs: Boolean,
    val isGoodWithCats: Boolean,
    val publishedAt: String
) {
    companion object{
        fun fromDomain(domainAnimal: AnimalWithDetails): CachedAnimalWithDetails {
            val details = domainAnimal.details
            return CachedAnimalWithDetails(
                animalId = domainAnimal.id,
                organizationId = details.organization.id,
                name = domainAnimal.name,
                type = domainAnimal.type,
                species = details.species,
                age = details.age.toString(),
                gender = details.gender.toString(),
                size = details.size.name,
                coat = details.coat.name,
                description = details.description,
                primaryBreed = details.breed.primary,
                secondaryBreed = details.breed.secondary,
                primaryColor = details.colors.primary,
                secondaryColor = details.colors.secondary,
                tertiaryColor = details.colors.tertiary,
                adoptionStatus = domainAnimal.adoptionStatus.toString(),
                isSpayedNeutered = details.healthDetails.isSpayedOrNeutered,
                isDeclawed = details.healthDetails.isDeclawed,
                hasSpecialNeeds = details.healthDetails.hasSpecialNeeds,
                areShotsCurrent = details.healthDetails.shotsAreCurrent,
                isGoodWithChildren = details.habitatAdaptation.goodWithChildren,
                isGoodWithDogs = details.habitatAdaptation.goodWithDogs,
                isGoodWithCats = details.habitatAdaptation.goodWithCats,
                publishedAt = domainAnimal.publishedAt.toString(),
            )
        }
    }

    fun toAnimalDomain(
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>): Animal {
        return Animal(
            id = animalId,
            name = name,
            type = type,
            media = Media(
                photos = photos.map { it.toDomain() },
                videos = videos.map { it.toDomain() }
            ),
            tags = tags.map { it.tag },
            adoptionStatus = AdoptionStatus.valueOf(adoptionStatus),
            publishedAt = DateTimeUtils.parse(publishedAt)
        )
    }

}