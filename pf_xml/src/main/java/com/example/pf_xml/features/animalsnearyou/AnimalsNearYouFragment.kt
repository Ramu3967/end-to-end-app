package com.example.pf_xml.features.animalsnearyou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.NoMoreAnimalsException
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouEvent
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewModel
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewModel.Companion.UI_PAGE_SIZE
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewState
import com.example.pf_xml.R
import com.example.pf_xml.databinding.FragmentAnimalsNearYouBinding
import com.example.pf_xml.presentation.AnimalsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimalsNearYouFragment: Fragment() {
    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private var _binding : FragmentAnimalsNearYouBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimalsNearYouViewModel by viewModels()

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var animalsAdapter: AnimalsAdapter

    private var isLoadingMoreAnimals = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalsNearYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialAnimalList()
    }

    private fun requestInitialAnimalList() {
        viewModel.onEvent(AnimalsNearYouEvent.RequestInitialAnimals)
    }

    private fun setupUI() {
        animalsAdapter = createAdapter()
        setupRecyclerView(animalsAdapter)
        observeViewStateUpdates()
    }

    private fun observeViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                updateScreenState(state)
            }
        }
    }

    private fun updateScreenState(state: AnimalsNearYouViewState) {
        binding.progressBar.isVisible = state.loading
        animalsAdapter.submitList(state.dataAnimals)
        handleFailure(state.failure)

        isLoadingMoreAnimals = state.loading
        isLastPage = state.failure is NoMoreAnimalsException
    }

    private fun handleFailure(failure: Throwable?) {
        val unhandledFailure = failure ?: return
        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if(unhandledFailure.message.isNullOrEmpty()) fallbackMessage else unhandledFailure.message!!
        if(snackbarMessage.isNotEmpty()){
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView(animalsAdapter: AnimalsAdapter) {
        layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
        binding.animalsRecyclerView.apply {
            adapter = animalsAdapter
            layoutManager = this@AnimalsNearYouFragment.layoutManager
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener())
        }
    }

    private fun createAdapter(): AnimalsAdapter {
        return AnimalsAdapter()
    }

    private fun createInfiniteScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoadingMoreAnimals && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= UI_PAGE_SIZE) {
                        loadMoreItems()
                    }
                }
            }
        }
    }

    private fun loadMoreItems() {
        isLoadingMoreAnimals = true
        viewModel.onEvent(AnimalsNearYouEvent.RequestMoreAnimals)
    }
}
