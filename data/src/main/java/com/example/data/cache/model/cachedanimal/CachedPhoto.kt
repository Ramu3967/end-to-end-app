package com.example.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.model.animal.Media

/**
 * This table/entity is dependent on the CachedAnimal table. It has one to many relationship (1 anim
 *  can have multiple photos/videos)
 */

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = CachedAnimalWithDetails::class,
            parentColumns = ["animalId"],
            childColumns = ["animalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("animalId")]
)

data class CachedPhoto(
    @PrimaryKey(autoGenerate = true)
    val photoId: Long = 0,
    val animalId: Long,
    val medium: String,
    val full: String
) {
    companion object {
        fun fromDomain(animalId: Long, photo: Media.Photo) = run {
            val (med, full) = photo
            CachedPhoto(animalId = animalId, medium = med, full = full)
        }
    }

    fun toDomain() = Media.Photo(medium = medium, full = full)

}


