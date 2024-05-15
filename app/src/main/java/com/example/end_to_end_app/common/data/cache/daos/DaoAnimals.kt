package com.example.end_to_end_app.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedAnimalWithDetails
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedPhoto
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedTag
import com.example.end_to_end_app.common.data.cache.model.cachedanimal.CachedVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoAnimals{

    // accessing multiple tables in a single transaction
    @Transaction
    @Query("select * from animals")
    fun getAllAnimals(): Flow<List<CachedAnimalAggregate>>

    suspend fun saveAllAnimals(animals: List<CachedAnimalAggregate>){
        animals.forEach {agg ->
            insertAnimalAggregate(
                animals = agg.animalWithDetails,
                photos = agg.photos,
                videos = agg.videos,
                tags = agg.tags

            )
        }
    }

    // NOTE - cannot insert Animal Aggregate as it is not an entity but a relationship between them
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimalAggregate(
        animals: CachedAnimalWithDetails,
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>
    )
}

