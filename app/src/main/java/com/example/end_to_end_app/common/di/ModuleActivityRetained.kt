package com.example.end_to_end_app.common.di

import com.example.end_to_end_app.common.data.PetFinderAnimalRepository
import com.example.end_to_end_app.common.domain.model.repos.AnimalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ModuleActivityRetained {

    @Binds
    @ActivityRetainedScoped
    // to survive the config changes and live as long as the activity
    abstract fun bindAnimalRepository(repository: PetFinderAnimalRepository): AnimalRepository
}