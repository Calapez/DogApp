package com.brunoponte.dogapp.ui.breedSearchList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.databinding.FragmentBreedSearchListBinding
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.ui.breedSearchList.listAdapter.BreedSearchListAdapter
import com.brunoponte.dogapp.ui.breedSearchList.listAdapter.BreedSearchListInteraction
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

        setupViewModelObservers()

        binding.searchView.doOnTextChanged { text, _, _, _ ->
            viewModel.searchBreeds(text.toString())
        }
    }

    override fun onClick(position: Int, breed: Breed) {
        val action = BreedSearchListFragmentDirections.actionBreedSearchListFragmentToBreedDetailsFragment(breed.id)
        findNavController().navigate(action)
    }

    private fun setupViewModelObservers() {
        viewModel.breeds.observe(viewLifecycleOwner) { breeds ->
            listAdapter.submitList(breeds?.map { it.copy() })
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}