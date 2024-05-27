package com.example.data.cache.model.cachedanimal

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.domain.model.animal.details.AnimalWithDetails

@Entity
data class CachedAnimalAggregate(
    @Embedded
    val animal: CachedAnimalWithDetails,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "animalId"
    )
    val photos: List<CachedPhoto>,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "animalId"
    )
    val videos: List<CachedVideo>,

    // many-to-many relation, hence the cross-ref table
    @Relation(
        parentColumn = "animalId",
        entityColumn = "tag",
        associateBy = Junction(CachedAnimalTagCrossRef::class)
    )
    val tags: List<CachedTag>

    ){
    companion object {
        fun fromDomain(animalWithDetails: AnimalWithDetails): CachedAnimalAggregate {
            return CachedAnimalAggregate(
                animal = CachedAnimalWithDetails.fromDomain(animalWithDetails),
                photos = animalWithDetails.media.photos.map {
                    CachedPhoto.fromDomain(animalWithDetails.id, it)
                },
                videos = animalWithDetails.media.videos.map {
                    CachedVideo.fromDomain(animalWithDetails.id, it)
                },
                tags =  animalWithDetails.tags.map { CachedTag(it) }
            )
        }
    }
}
