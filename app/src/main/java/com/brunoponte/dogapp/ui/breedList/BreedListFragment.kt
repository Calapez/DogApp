package com.brunoponte.dogapp.ui.breedList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.brunoponte.dogapp.databinding.FragmentBreedListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedListFragment : Fragment() {

    private lateinit var binding: FragmentBreedListBinding
    private val viewModel: BreedListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


}