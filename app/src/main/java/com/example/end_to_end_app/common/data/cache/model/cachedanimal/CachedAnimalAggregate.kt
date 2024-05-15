package com.example.end_to_end_app.common.data.cache.model.cachedanimal

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity
data class CachedAnimalAggregate(
    @Embedded
    val animalWithDetails: CachedAnimalWithDetails,

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

    )
