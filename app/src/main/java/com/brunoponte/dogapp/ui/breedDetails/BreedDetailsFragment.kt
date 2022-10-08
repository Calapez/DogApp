package com.brunoponte.dogapp.ui.breedDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.databinding.FragmentBreedDetailsBinding
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.helpers.Util
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBreedDetailsBinding
    private val viewModel: BreedDetailsViewModel by viewModels()
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
        viewModel.selectedBreed.observe(viewLifecycleOwner) { breed ->
            setupView(breed)
        }
    }

    private fun setupView(breed: Breed?) {
        breed?.let {
            val notApplicableText = getString(R.string.not_applicable)

            Glide.with(this)
                .load(String.format(Util.dogImageApiUrlTemplate, breed.referenceImageId))
                .into(binding.breedImage)

            binding.breedNameText.text = breed.name ?: notApplicableText

            binding.breedGroupText.text = breed.breedGroup ?: notApplicableText

            binding.breedOriginText.text = breed.origin ?: notApplicableText

            binding.breedTemperamentText.text = breed.temperament ?: notApplicableText
        }

    }
}