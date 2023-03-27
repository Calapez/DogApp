package com.brunoponte.dogapp.ui.breedSearchList

import com.brunoponte.dogapp.ui.BreedItemViewState

sealed class BreedSearchListViewState {
    object Loading: BreedSearchListViewState()
    object Error: BreedSearchListViewState() // TOTO (errorMsg: String = "")
    data class Content(val breeds: List<BreedItemViewState>): BreedSearchListViewState()
}
