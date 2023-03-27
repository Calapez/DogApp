package com.brunoponte.dogapp.presentation.breedSearchList

import com.brunoponte.dogapp.presentation.BreedItemViewState

sealed class BreedSearchListViewState {
    object Loading: BreedSearchListViewState()
    object Error: BreedSearchListViewState() // TOTO (errorMsg: String = "")
    data class Content(val breeds: List<BreedItemViewState>): BreedSearchListViewState()
}
