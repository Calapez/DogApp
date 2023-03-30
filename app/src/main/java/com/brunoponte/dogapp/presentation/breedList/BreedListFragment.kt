package com.brunoponte.dogapp.presentation.breedList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.databinding.FragmentBreedListBinding
import com.brunoponte.dogapp.domain.SortMode
import com.brunoponte.dogapp.presentation.breedList.listAdapter.BreedListAdapter
import com.brunoponte.dogapp.presentation.breedList.listAdapter.BreedListInteraction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedListFragment : Fragment(), BreedListInteraction {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: BreedListViewModel by viewModels()

    private lateinit var binding: FragmentBreedListBinding

    private lateinit var listAdapter: BreedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFirstBreeds()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedListBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = BreedListAdapter(this, viewModel.listMode.value!!).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy
                .PREVENT_WHEN_EMPTY
        }

        binding.breedListRecyclerView.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }

            recyclerView.adapter = listAdapter
        }

        binding.imageSort.setOnClickListener {
            viewModel.onSortClicked()
        }

        binding.imageListMode.setOnClickListener {
            viewModel.onListModeClicked()
        }

        setupViewModelObservers()
    }

    override fun onClick(position: Int, breedId: Int) {
        val action = BreedListFragmentDirections.actionBreedListFragmentToBreedDetailsFragment(breedId)
        findNavController().navigate(action)
    }

    override fun onIndexReached(index: Int) {
        // Reached a new element in Recycler View, update scroll position in VM
        viewModel.onChangeBreedScrollPosition(index)
    }

    private fun setupViewModelObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when(viewState) {
                is BreedListViewState.Content -> {
                    binding.loadingProgressIndicator.isVisible = false
                    binding.errorView.isVisible = false
                    binding.breedListRecyclerView.isVisible = true
                    listAdapter.submitList(viewState.breeds)
                }
                is BreedListViewState.Error -> {
                    binding.breedListRecyclerView.isVisible = false
                    binding.loadingProgressIndicator.isVisible = false
                    binding.errorView.isVisible = true
                }
                BreedListViewState.Loading -> {
                    binding.errorView.isVisible = false
                    binding.breedListRecyclerView.isVisible = true
                    binding.loadingProgressIndicator.isVisible = true
                }
            }
        }

        viewModel.sortMode.observe(viewLifecycleOwner) { sortMode ->
            binding.imageSort.setImageResource(when (sortMode) {
                SortMode.ASC -> R.drawable.ic_asc
                SortMode.DESC -> R.drawable.ic_desc
            })
        }

        viewModel.listMode.observe(viewLifecycleOwner) { listMode ->
            listAdapter.listMode = listMode

            // Change icon
            when (listMode) {
                ListMode.LINEAR -> {
                    binding.imageListMode.setImageResource(R.drawable.ic_list)
                    binding.breedListRecyclerView.layoutManager = LinearLayoutManager(context).also {
                        it.orientation = LinearLayoutManager.VERTICAL
                    }

                }

                ListMode.GRID -> {
                    binding.imageListMode.setImageResource(R.drawable.ic_grid)
                    binding.breedListRecyclerView.layoutManager = GridLayoutManager(context, 2).also {
                        it.orientation = LinearLayoutManager.VERTICAL
                    }
                }
            }

            binding.breedListRecyclerView.adapter = listAdapter
        }
    }
}