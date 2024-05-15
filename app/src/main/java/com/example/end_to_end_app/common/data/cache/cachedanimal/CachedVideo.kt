package com.example.end_to_end_app.common.data.cache.cachedanimal

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.end_to_end_app.common.domain.model.animal.Media

/**
 * This table/entity is dependent on the CachedAnimal table. It has one to many relationship (1 anim
 *  can have multiple photos/videos)
 */

@Entity(
    tableName = "videos",
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
data class CachedVideo(
    @PrimaryKey(autoGenerate = true)
    val videoId: Long = 0,
    val animalId: Long,
    val video: String
) {
    companion object {
        fun fromDomain(animalId: Long, video: Media.Video): CachedVideo {
            return CachedVideo(animalId = animalId, video = video.video)
        }
    }

    fun toDomain(): Media.Video = Media.Video(video)
}

