package com.brunoponte.dogapp.ui.breedSearch

import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedSearchViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository
) : ViewModel() {

}