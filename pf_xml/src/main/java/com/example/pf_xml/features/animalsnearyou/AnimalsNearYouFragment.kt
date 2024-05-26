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
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouEvent
import com.example.pf_utils.features.animalsnearyou.presentation.AnimalsNearYouViewModel
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
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun observeViewStateUpdates(adapter: AnimalsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                updateScreenState(it,adapter)
            }
        }
    }

    private fun updateScreenState(
        state: AnimalsNearYouViewState,
        adapter: AnimalsAdapter
    ){
        // consume all the fields of your state
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.dataAnimals)
        handleFailure(state.failure)
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
        binding.animalsRecyclerView.apply{
            adapter = animalsAdapter
            layoutManager= GridLayoutManager(requireContext(),ITEMS_PER_ROW)
            setHasFixedSize(true)
        }
    }

    private fun createAdapter(): AnimalsAdapter {
        return AnimalsAdapter()
    }
}