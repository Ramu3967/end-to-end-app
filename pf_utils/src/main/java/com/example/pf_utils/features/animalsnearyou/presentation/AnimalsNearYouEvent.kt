package com.example.pf_utils.features.animalsnearyou.presentation

sealed class AnimalsNearYouEvent {
    object RequestInitialAnimals:AnimalsNearYouEvent()
    object RequestMoreAnimals: AnimalsNearYouEvent()
}