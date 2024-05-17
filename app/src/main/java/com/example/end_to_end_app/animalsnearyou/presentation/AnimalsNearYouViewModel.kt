package com.example.end_to_end_app.animalsnearyou.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.end_to_end_app.animalsnearyou.domain.usecases.GetAnimals
import com.example.end_to_end_app.animalsnearyou.domain.usecases.RequestNextPageOfAnimals
import com.example.end_to_end_app.common.domain.NetworkUnavailableException
import com.example.end_to_end_app.common.domain.NoMoreAnimalsException
import com.example.end_to_end_app.common.presentation.model.UIAnimal
import com.example.end_to_end_app.common.presentation.model.mappers.UiAnimalMapper
import com.example.end_to_end_app.common.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsNearYouViewModel @Inject constructor(
    private val getAnimals: GetAnimals,
    private val requestMoreAnimals: RequestNextPageOfAnimals,
    private val uiAnimalMapper: UiAnimalMapper
): ViewModel(){

    // holds the state of AnimalsNearYou screen
    private val _state = MutableStateFlow(AnimalsNearYouViewState())
    val state: StateFlow<AnimalsNearYouViewState>
        get() = _state.asStateFlow()

    init {
        subscribeToAnimalUpdates()
    }

    private fun subscribeToAnimalUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            // invoking the use-case
            getAnimals().map {animals ->
                animals.map { uiAnimalMapper.mapToView(it) }
            }.collect{
                    onNewAnimals(it)
                }
            // TODO: handle failures
        }
    }

    fun onEvent(event: AnimalsNearYouEvent){
        when(event){
            AnimalsNearYouEvent.RequestInitialAnimals -> {
                Log.d("AnimalVM", "onEvent: Hello event")
                loadAnimals()
            }
        }
    }

    private fun loadAnimals(){
        if(_state.value.dataAnimals.isEmpty())
            loadNextAnimalPage()
    }

    private fun loadNextAnimalPage() {
        val errorMessage = "Failed to fetch animals"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage){
            onFailure(it)
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            // saves animals into db and the ui gets updated
            requestMoreAnimals(pageToLoad = 3)
        }
    }

    private fun onNewAnimals(animals: List<UIAnimal>){
        _state.update { oldState ->
            oldState.copy(
                loading = false,
                dataAnimals = animals
            )
        }
    }

    private fun onFailure(failure: Throwable) {
        Log.e("TAG", "onFailure: ${failure.message}")
        when(failure){
            is NoMoreAnimalsException -> {
                _state.update { oldState ->
                    oldState.copy(
                        loading = false,
                        failure = failure,
                        dataAnimals = emptyList()
                    )
                }
            }
            is NetworkUnavailableException -> {
                _state.update { oldState ->
                    oldState.copy(
                        loading = false,
                        failure = failure
                    )
                }
            }
        }
    }

}