package com.example.pf_utils.model.mappers

import com.example.domain.model.animal.Animal
import com.example.pf_utils.model.UIAnimal
import javax.inject.Inject

class UiAnimalMapper @Inject constructor(): UiMapper<Animal, UIAnimal> {
    override fun mapToView(input: Animal): UIAnimal {
        return UIAnimal(
            input.id,
            input.name,
            input.media.getFirstSmallestAvailablePhoto()
        )
    }
}