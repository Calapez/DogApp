package com.brunoponte.dogapp.presentation.breedSearchList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.databinding.FragmentBreedSearchListBinding
import com.brunoponte.dogapp.presentation.breedSearchList.listAdapter.BreedSearchListAdapter
import com.brunoponte.dogapp.presentation.breedSearchList.listAdapter.BreedSearchListInteraction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedSearchListFragment : Fragment(), BreedSearchListInteraction {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val viewModel: BreedSearchListViewModel by viewModels()

    private lateinit var binding: FragmentBreedSearchListBinding
    private val listAdapter = BreedSearchListAdapter(this).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy
            .PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedSearchListBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.breedsRecyclerView.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }

            recyclerView.adapter = listAdapter
        }

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUi(viewState)
        }

        binding.searchView.doOnTextChanged { text, _, _, _ ->
            viewModel.searchBreeds(text.toString())
        }
    }

    override fun onClick(position: Int, breedId: Int) {
        val action = BreedSearchListFragmentDirections.actionBreedSearchListFragmentToBreedDetailsFragment(breedId)
        findNavController().navigate(action)
    }

    private fun updateUi(viewState: BreedSearchListViewState) {
        when(viewState) {
            is BreedSearchListViewState.Content -> {
                binding.progress.isVisible = false
                binding.errorView.isVisible = false
                binding.breedsRecyclerView.isVisible = true
                listAdapter.submitList(viewState.breeds.map { it.copy() })
            }
            is BreedSearchListViewState.Error -> {
                binding.breedsRecyclerView.isVisible = false
                binding.progress.isVisible = false
                binding.errorView.isVisible = true
            }
            BreedSearchListViewState.Loading -> {
                binding.errorView.isVisible = false
                binding.breedsRecyclerView.isVisible = true
                binding.progress.isVisible = true
            }
        }
    }
}