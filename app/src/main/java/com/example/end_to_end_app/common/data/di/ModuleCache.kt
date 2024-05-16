package com.example.end_to_end_app.common.data.di

import android.content.Context
import androidx.room.Room
import com.example.end_to_end_app.common.data.cache.ICache
import com.example.end_to_end_app.common.data.cache.PetDatabase
import com.example.end_to_end_app.common.data.cache.RoomCache
import com.example.end_to_end_app.common.data.cache.daos.DaoAnimals
import com.example.end_to_end_app.common.data.cache.daos.DaoOrganization
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * reason for not using an object but an abstract class - @Binds should be in an abstract context and
 * object class doesn't provide it. Also using the companion object to get the benefits of the object class, for non-
 * bindable providers - concrete classes.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleCache {

    companion object{

        @Provides
        @Singleton
        fun provideRoomDatabase(@ApplicationContext context: Context): PetDatabase{
            return Room.databaseBuilder(
                context,
                PetDatabase::class.java,
                "petsave.db"
            ).build()
        }


        @Provides
        @Singleton
        fun provideAnimalsDao(db: PetDatabase): DaoAnimals = db.getAnimalsDao()


        @Provides
        @Singleton
        fun provideOrganizationsDao(db: PetDatabase): DaoOrganization = db.getOrganizationDao()

    }

    @Binds
    abstract fun bindCache(cache: RoomCache): ICache
}