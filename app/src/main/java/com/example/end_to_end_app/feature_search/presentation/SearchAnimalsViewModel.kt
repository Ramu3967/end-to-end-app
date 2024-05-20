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

    private val _state = MutableStateFlow(SearchAnimalState())
    val state: StateFlow<SearchAnimalState>
        get() = _state.asStateFlow()

    fun onEvent(event:SearchAnimalEvents ){
        when(event){
            SearchAnimalEvents.GetAnimalTypesEvent -> {
                Log.d("AnimalSearchVM", "onEvent: animal-types event")
                viewModelScope.launch(Dispatchers.IO) {
                    val searchFilters = getSearchFilters()
                    updateStateWithFilterValues(searchFilters.ages,searchFilters.types)
                }
            }
            else -> {}
        }
    }

    private fun updateStateWithFilterValues(ages: List<String>, types: List<String>){
        _state.update {
            // update the state accordingly
            it.copy(ageFilterValues = ages, typeFilterValues =  types)
        }
    }
}