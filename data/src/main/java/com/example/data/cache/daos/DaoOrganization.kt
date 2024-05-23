package com.example.end_to_end_app.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.data.cache.model.cachedorganization.CachedOrganization

@Dao
interface DaoOrganization{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(organizations: List<CachedOrganization>)
}