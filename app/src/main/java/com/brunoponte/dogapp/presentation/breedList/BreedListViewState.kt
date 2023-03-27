package com.brunoponte.dogapp.presentation.breedList

import com.brunoponte.dogapp.presentation.BreedItemViewState

sealed class BreedListViewState {
    object Loading: BreedListViewState()
    data class Error(val errorMsg: String): BreedListViewState()
    data class Content(val breeds: List<BreedItemViewState>): BreedListViewState()
}
