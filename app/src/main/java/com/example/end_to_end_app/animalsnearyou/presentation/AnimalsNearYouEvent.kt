package com.example.end_to_end_app.animalsnearyou.presentation

sealed class AnimalsNearYouEvent {
    object RequestInitialAnimals:AnimalsNearYouEvent()
    object RequestMoreAnimals: AnimalsNearYouEvent()
}