package com.brunoponte.dogapp.ui.breedDetails

import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository
) : ViewModel() {

}