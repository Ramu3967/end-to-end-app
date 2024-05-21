package com.example.end_to_end_app.feature_search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.end_to_end_app.feature_search.domain.usecases.GetSearchFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimalsViewModel @Inject constructor(
    private val getSearchFilters: GetSearchFilters
): ViewModel() {

    private val _state = MutableStateFlow(SearchAnimalViewState())
    val state: StateFlow<SearchAnimalViewState>
        get() = _state.asStateFlow()


    private val queryInputFlow = MutableStateFlow("")
    private val ageInputFlow = MutableStateFlow("")
    private val typeInputFlow = MutableStateFlow("")

    fun onEvent(event:SearchAnimalEvents ){
        when(event){
            is SearchAnimalEvents.PrepareForSearchEvent -> {
                Log.d("AnimalSearchVM", "onEvent: prepare search event")
                viewModelScope.launch(Dispatchers.IO) {
                    val searchFilters = getSearchFilters()
                    updateStateWithFilterValues(searchFilters.ages,searchFilters.types)
                }
            }
            is SearchAnimalEvents.AgeValueSelected ->{  updateAgeInput(event.age)
                Log.d("AnimalSearchVM", "onEvent: age event")}
            is SearchAnimalEvents.TypeValueSelected ->{  updateTypeInput(event.type)
                Log.d("AnimalSearchVM", "onEvent: type event")}
            is SearchAnimalEvents.QueryInput ->{  updateQueryInput(event.input)
                Log.d("AnimalSearchVM", "onEvent: query event")}
        }
    }

    private fun updateQueryInput(input: String) {
        queryInputFlow.value = input
    }

    private fun updateTypeInput(type: String) {
        typeInputFlow.value = type
    }

    private fun updateAgeInput(age: String) {
        ageInputFlow.value = age
    }

    private fun updateStateWithFilterValues(ages: List<String>, types: List<String>){
        _state.update {
            // update the state accordingly
            it.copy(ageFilterValues = ages, typeFilterValues =  types)
        }
    }


}