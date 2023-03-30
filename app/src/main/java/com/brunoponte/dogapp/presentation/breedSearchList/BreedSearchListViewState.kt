package com.brunoponte.dogapp.presentation.breedSearchList

import com.brunoponte.dogapp.presentation.BreedUiItem

sealed class BreedSearchListViewState {
    object Loading: BreedSearchListViewState()
    data class Error(val errorMsg: String): BreedSearchListViewState() // TOTO (errorMsg: String = "")
    data class Content(val breeds: List<BreedUiItem>): BreedSearchListViewState()
}
