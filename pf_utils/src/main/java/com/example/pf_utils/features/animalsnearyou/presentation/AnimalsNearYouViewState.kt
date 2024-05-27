package com.example.pf_utils.features.animalsnearyou.presentation

import com.example.pf_utils.model.UIAnimal


/**
 * using view states to avoid impossible states (to avoid mutability problems)
 */
data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val dataAnimals: List<UIAnimal> = emptyList(),
    val failure: Throwable? = null
)