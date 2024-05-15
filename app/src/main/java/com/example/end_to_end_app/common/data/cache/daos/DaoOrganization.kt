package com.example.end_to_end_app.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.end_to_end_app.common.data.cache.model.cachedorganization.CachedOrganization

@Dao
interface DaoOrganization{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(organizations: List<CachedOrganization>)
}