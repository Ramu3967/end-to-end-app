package com.example.end_to_end_app.feature_search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.end_to_end_app.common.domain.model.animal.Animal
import com.example.end_to_end_app.common.domain.model.pagination.Pagination
import com.example.end_to_end_app.common.presentation.model.mappers.UiAnimalMapper
import com.example.end_to_end_app.common.utils.createExceptionHandler
import com.example.end_to_end_app.feature_search.domain.models.SearchParameters
import com.example.end_to_end_app.feature_search.domain.models.SearchResults
import com.example.end_to_end_app.feature_search.domain.usecases.GetSearchFilters
import com.example.end_to_end_app.feature_search.domain.usecases.SearchAnimals
import com.example.end_to_end_app.feature_search.domain.usecases.SearchAnimalsRemotely
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class SearchAnimalsViewModel @Inject constructor(
    private val getSearchFilters: GetSearchFilters,
    private val searchAnimals: SearchAnimals,
    private val searchAnimalsRemotely: SearchAnimalsRemotely,
    private val uiAnimalMapper: UiAnimalMapper
): ViewModel() {

    private val _state = MutableStateFlow(SearchAnimalViewState())
    val state: StateFlow<SearchAnimalViewState>
        get() = _state.asStateFlow()

    private var currentPage = 1
    private val queryInputFlow = MutableStateFlow("")
    private val ageInputFlow = MutableStateFlow("")
    private val typeInputFlow = MutableStateFlow("")

    // to cancel previous searches
    private var remoteSearchJob: Job =  Job()

    fun onEvent(event:SearchAnimalEvents ){
        when(event){
            is SearchAnimalEvents.PrepareForSearchEvent -> {
                Log.d("AnimalSearchVM", "onEvent: prepare search event")
                loadFilterValues()
                setupSearchSubscription()
            }
            else -> onSearchParamsUpdate(event)

        }
    }

    private fun onSearchParamsUpdate(event: SearchAnimalEvents) {
        // cancel previous searches
        remoteSearchJob.cancel(CancellationException("New search params incoming!!"))
        when(event){
            is SearchAnimalEvents.AgeValueSelected ->{  updateAgeInput(event.age)
                Log.d("AnimalSearchVM", "onEvent: age event")}
            is SearchAnimalEvents.TypeValueSelected ->{  updateTypeInput(event.type)
                Log.d("AnimalSearchVM", "onEvent: type event")}
            is SearchAnimalEvents.QueryInput ->{  updateQuery(event.input)
                Log.d("AnimalSearchVM", "onEvent: query event")}
            else -> Log.e("SearchAnimalsVM", "onSearchParamsUpdate: Wrong Search Event" )
        }
    }

    private fun updateQuery(input: String) {
        updateQueryInput(input)
        if(input.isEmpty()) setSearchingState()
        else setNoQuerySearchState()
        resetPagination()
    }

    private fun resetPagination() {
        currentPage = 1
    }

    private fun loadFilterValues() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error in getting the filter values"){
            onFailure(it)
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val searchFilters = getSearchFilters()
            updateStateWithFilterValues(searchFilters.ages, searchFilters.types)
        }
    }

    private fun setupSearchSubscription() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error in setting up the search subscription"){
                onFailure(it)
            }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            searchAnimals(queryInputFlow, ageInputFlow, typeInputFlow)
                .collect{
                    onSearchResults(it)
                }
        }
    }

    private fun onSearchResults(searchResults: SearchResults) {
        val (animals, searchParams) = searchResults
        if(animals.isEmpty())
            /*remote search*/
            onEmptyCacheResults(searchParams)
        else onAnimalsList(animals)
    }

    private fun onEmptyCacheResults(searchParams: SearchParameters) {
        Log.e("AnimalSearchVM", "onEmptyCacheResults: $searchParams", )

        _state.update { oldState ->
            oldState.updateToSearchingRemotely()
        }
        searchRemotely(searchParams)
    }

    private fun searchRemotely(searchParams: SearchParameters) {
        // another use-case
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error in getting remote animals"){
                onFailure(it)
            }
        remoteSearchJob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val k= searchAnimalsRemotely(searchParams, currentPage++, Pagination.DEFAULT_PAGE_SIZE)
            onPaginationInfoObtained(k)
        }
        remoteSearchJob.invokeOnCompletion {
            Log.e("AnimalSearchVm", "searchRemotely: ${it?.message}" )
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
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

    private fun onAnimalsList(animals: List<Animal>){
        Log.e("AnimalSearchVM", "onAnimalsList: ${animals}", )
        val uiAnimals = animals.map {
            uiAnimalMapper.mapToView(it)
        }
        _state.update {
            it.copy(searchResults = uiAnimals)
        }
    }

    private fun setSearchingState(){
        _state.update { oldState -> oldState.updateToSearching()
        }
    }

    private fun setNoQuerySearchState(){
        _state.update { oldState -> oldState.updateToNoSearchQuery() }
    }

    private fun updateStateWithFilterValues(ages: List<String>, types: List<String>){
        _state.update {
            // update the state accordingly
            it.copy(ageFilterValues = ages, typeFilterValues =  types)
        }
    }

    private fun onFailure(throwable: Throwable) {
        Log.e("AnimalSearchVM", "onFailure: $throwable ")
    }


}