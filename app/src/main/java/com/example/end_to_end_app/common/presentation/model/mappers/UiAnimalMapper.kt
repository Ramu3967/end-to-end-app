package com.example.end_to_end_app.common.presentation.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.presentation.model.UIAnimal
import javax.inject.Inject

class UiAnimalMapper @Inject constructor() : UiMapper<Animal, UIAnimal> {
    override fun mapToView(input: Animal): UIAnimal {
        return UIAnimal(
            input.id,
            input.name,
            input.media.photos.first().getSmallestAvailablePhoto()
        )
    }
}