package com.example.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "cross_ref",
    primaryKeys = ["animalId", "tag"],
    indices = [Index("tag")]
)
data class CachedAnimalTagCrossRef(
    val animalId: Long,
    val tag: String
)
