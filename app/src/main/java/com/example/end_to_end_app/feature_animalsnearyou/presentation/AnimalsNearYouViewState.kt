package com.example.end_to_end_app.feature_animalsnearyou.presentation

import com.example.end_to_end_app.common.presentation.model.UIAnimal

/**
 * using view states to avoid impossible states (to avoid mutability problems)
 */
data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val dataAnimals: List<UIAnimal> = emptyList(),
    val failure: Throwable? = null
)