package com.example.end_to_end_app.common.data.di

import com.example.end_to_end_app.common.data.preferences.IPreferences
import com.example.end_to_end_app.common.data.preferences.PetFinderPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ModulePreferences {

    @Binds
    fun providePreferences(preferences: PetFinderPreferences): IPreferences
}