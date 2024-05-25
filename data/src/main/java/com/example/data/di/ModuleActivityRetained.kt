package com.example.data.di

import com.example.data.PetFinderAnimalRepository
import com.example.domain.model.repos.AnimalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ModuleActivityRetained {

    @Binds @ActivityRetainedScoped
    abstract fun bindAnimalRepoImpl(petFinderRepo: PetFinderAnimalRepository): AnimalRepository
}