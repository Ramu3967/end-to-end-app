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
abstract class DaoAnimals {
    @Transaction
    @Query("select * from animals")
    abstract fun getAllAnimals():Flow<List<CachedAnimalAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAnimalAggregate(
        animal:CachedAnimalWithDetails,
        photos:List<CachedPhoto>,
        videos:List<CachedVideo>,
        tags:List<CachedTag>
    )

    suspend fun insertAnimalsWithDetails(animalAggregates: List<CachedAnimalAggregate>) {
        animalAggregates.forEach {
            insertAnimalAggregate(
                it.animal,
                it.photos,
                it.videos,
                it.tags
            )
        }
    }

    @Query("select distinct type from animals")
    abstract fun getAllTypes():List<String>


    @Query("select * from animals where name like '%' || :name || '%' and  age like '%' || :age || '%' and type like '%' || :type || '%'")
    abstract fun searchAnimalsWith(name: String, age: String, type: String): Flow<List<CachedAnimalAggregate>>
}

