package com.brunoponte.dogapp.presentation.breedDetails

import com.brunoponte.dogapp.domain.models.Breed

sealed class BreedDetailsViewState {
    object Loading : BreedDetailsViewState()
    data class Error(val errorMsg: String) : BreedDetailsViewState()
    data class Content(val breed: Breed?) : BreedDetailsViewState()
}
