package com.example.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tables 'Animals' and 'Tags' have many-to-many relationship. Therefore, we need a cross-ref table
 *  (CachedAnimalCrossRef).
 */

@Entity(tableName = "tags")
data class CachedTag(
    @PrimaryKey
    val tag: String
)