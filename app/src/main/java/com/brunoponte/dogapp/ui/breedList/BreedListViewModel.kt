package com.brunoponte.dogapp.ui.breedList

import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository
) : ViewModel() {

}