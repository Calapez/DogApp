package com.brunoponte.dogapp.ui.breedList

import com.brunoponte.dogapp.domainModels.Breed

sealed class BreedListViewState {
    object Loading: BreedListViewState()
    object Error: BreedListViewState()
    data class Content(val breeds: List<Breed>): BreedListViewState()
}
