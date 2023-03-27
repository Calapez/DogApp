package com.brunoponte.dogapp.ui.breedList

import com.brunoponte.dogapp.ui.BreedItemViewState

sealed class BreedListViewState {
    object Loading: BreedListViewState()
    object Error: BreedListViewState()
    data class Content(val breeds: List<BreedItemViewState>): BreedListViewState()
}
