package com.example.pf_xml.features.animalsnearyou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pf_xml.databinding.FragmentAnimalsNearYouBinding
import com.example.pf_xml.presentation.AnimalsAdapter

class AnimalsNearYouFragment: Fragment() {
    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private var _binding : FragmentAnimalsNearYouBinding? = null
    private val binding get() = _binding!!


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
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
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