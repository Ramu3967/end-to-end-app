package com.example.pf_xml.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.NoMoreAnimalsException
import com.example.pf_utils.features.search.presentation.SearchAnimalEvents
import com.example.pf_utils.features.search.presentation.SearchAnimalViewState
import com.example.pf_utils.features.search.presentation.SearchAnimalsViewModel
import com.example.pf_xml.R
import com.example.pf_xml.databinding.FragmentSearchBinding
import com.example.pf_xml.presentation.AnimalsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class AnimalsSearchFragment: Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private val viewModel: SearchAnimalsViewModel by viewModels()

    private val binding get() = _binding!!
    private var _binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        prepareForSearch()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun createAdapter(): AnimalsAdapter {
        return AnimalsAdapter()
    }

    private fun setupRecyclerView(searchAdapter: AnimalsAdapter) {
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
            setHasFixedSize(true)
        }
    }

    private fun setupFilterValues(
        filter: AutoCompleteTextView,
        filterValues: List<String>?
    ){
        if(filterValues.isNullOrEmpty()) return
        filter.setAdapter(createFilterAdapter(filterValues))
        filter.setText(viewModel.filterDefaultValue, false)
    }

    private fun createFilterAdapter(adapterValues: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, adapterValues)
    }

    private fun prepareForSearch(){
        setupFilterListeners()
        setupSearchViewListener()
        viewModel.onEvent(SearchAnimalEvents.PrepareForSearchEvent)
    }

    private fun setupSearchViewListener(){
        val searchView = binding.searchWidget.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onEvent(SearchAnimalEvents.QueryInput(query.orEmpty()))
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onEvent(SearchAnimalEvents.QueryInput(newText.orEmpty()))
                return true
            }

        })
    }

    private fun setupFilterListeners(){
        with(binding.searchWidget){
            setupFilterListenerFor(age){selectedAge ->
                viewModel.onEvent(SearchAnimalEvents.AgeValueSelected(selectedAge))
            }
            setupFilterListenerFor(type){selectedType ->
                viewModel.onEvent(SearchAnimalEvents.TypeValueSelected(selectedType))
            }
        }
    }

    private fun observeViewStateUpdates(searchAdapter: AnimalsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch(){
            viewModel.state.collect {
                updateScreenState(it, searchAdapter)
            }
        }
    }

    private fun updateScreenState(newState: SearchAnimalViewState, animalsAdapter: AnimalsAdapter) {
        val (
            inInitialState,
            searchResults,
            ageFilterValues,
            typeFilterValues,
            searchingRemotely,
            noResultsState,
            failure
        ) = newState

        updateInitialStateViews(inInitialState)
        updateRemoteStateViews(searchingRemotely)
        updateNoResultState(noResultsState)

        animalsAdapter.submitList(searchResults)
        with (binding.searchWidget) {
            setupFilterValues(age, ageFilterValues)
            setupFilterValues(type, typeFilterValues)
        }

        handleFailures(failure)
    }

    private fun updateNoResultState(noResultsState: Boolean) {
        binding.noSearchResultsText.isVisible = noResultsState
        binding.noSearchResultsImageView.isVisible = noResultsState
    }

    private fun updateInitialStateViews(inInitialState: Boolean) {
        binding.initialSearchText.isVisible = inInitialState
        binding.initialSearchImageView.isVisible = inInitialState
    }

    private fun updateRemoteStateViews(inRemoteSearch: Boolean){
        binding.searchRemotelyText.isVisible = inRemoteSearch
        binding.searchRemotelyProgressBar.isVisible = inRemoteSearch
    }

    private fun setupFilterListenerFor(filter: AutoCompleteTextView, block: (item: String) -> Unit) {
        filter.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            parent?.let {
                block(it.adapter.getItem(position) as String)
            }
        }
    }

    private fun handleFailures(failure: Throwable?) {
        val unhandledFailure = failure ?: return

        handleThrowable(unhandledFailure)
    }

    private fun handleThrowable(exception: Throwable) {
        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = when (exception) {
            is NoMoreAnimalsException -> exception.message ?: fallbackMessage
            is IOException -> fallbackMessage
            else -> ""
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
