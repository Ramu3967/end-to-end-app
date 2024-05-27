package com.example.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.cache.daos.DaoAnimals
import com.example.end_to_end_app.common.data.cache.daos.DaoOrganization
import com.example.data.cache.model.cachedanimal.CachedAnimalTagCrossRef
import com.example.data.cache.model.cachedanimal.CachedAnimalWithDetails
import com.example.data.cache.model.cachedanimal.CachedPhoto
import com.example.data.cache.model.cachedanimal.CachedTag
import com.example.data.cache.model.cachedanimal.CachedVideo
import com.example.data.cache.model.cachedorganization.CachedOrganization

@Database(
    entities = [
        CachedPhoto::class,
        CachedVideo::class,
        CachedTag::class,
        CachedAnimalWithDetails::class,
        CachedOrganization::class,
        CachedAnimalTagCrossRef::class
    ],
    version = 1
)
abstract class PetDatabase: RoomDatabase() {
    abstract fun getAnimalsDao(): DaoAnimals
    abstract fun getOrganizationDao(): DaoOrganization
}