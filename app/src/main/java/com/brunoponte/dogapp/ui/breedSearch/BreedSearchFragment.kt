package com.brunoponte.dogapp.ui.breedSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.brunoponte.dogapp.databinding.FragmentBreedSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedSearchFragment : Fragment() {

    private lateinit var binding: FragmentBreedSearchBinding
    private val viewModel: BreedSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedSearchBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }


}