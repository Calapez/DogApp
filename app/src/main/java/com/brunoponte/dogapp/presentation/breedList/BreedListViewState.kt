package com.brunoponte.dogapp.presentation.breedList

import com.brunoponte.dogapp.presentation.BreedItemViewState

sealed class BreedListViewState {
    object Loading: BreedListViewState()
    object Error: BreedListViewState()
    data class Content(val breeds: List<BreedItemViewState>): BreedListViewState()
}
