package com.brunoponte.dogapp.presentation.breedDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.databinding.FragmentBreedDetailsBinding
import com.brunoponte.dogapp.data.network.utils.Endpoints
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailsFragment : Fragment() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: BreedDetailsViewModel by viewModels()

    private lateinit var binding: FragmentBreedDetailsBinding
    private val args: BreedDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breedId = args.breedId
        viewModel.getBreedFromId(breedId)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            setupView(viewState)
        }
    }

    private fun setupView(viewState: BreedDetailsViewState) {
        when(viewState) {
            is BreedDetailsViewState.Content -> {
                binding.loadingProgressIndicator.isVisible = false
                binding.breedDetailsView.isVisible = true

                viewState.breed?.let {
                    val notApplicableText = getString(R.string.not_applicable)

                    Glide.with(this)
                        .load(String.format(Endpoints.dogImageApiEndpointTemplate, it.referenceImageId))
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_no_image)
                        .into(binding.breedImage)

                    binding.breedNameText.text = it.name ?: notApplicableText

                    binding.breedGroupText.text = it.breedGroup ?: notApplicableText

                    binding.breedOriginText.text = it.origin ?: notApplicableText

                    binding.breedTemperamentText.text = it.temperament ?: notApplicableText
                }
            }
            is BreedDetailsViewState.Error -> {
                binding.loadingProgressIndicator.isVisible = false
                binding.breedDetailsView.isVisible = false
            }
            BreedDetailsViewState.Loading -> {
                binding.breedDetailsView.isVisible = false
                binding.loadingProgressIndicator.isVisible = true
            }
        }


    }
}