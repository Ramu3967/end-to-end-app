package com.example.end_to_end_app.animalsnearyou.presentation

import androidx.lifecycle.ViewModel
import com.example.end_to_end_app.animalsnearyou.domain.usecases.GetAnimals
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimalsNearYouViewModel @Inject constructor(
    private val getAnimals: GetAnimals
): ViewModel(){

}