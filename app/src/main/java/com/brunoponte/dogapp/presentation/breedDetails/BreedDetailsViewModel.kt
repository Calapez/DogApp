package com.brunoponte.dogapp.presentation.breedDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.domain.Response
import com.brunoponte.dogapp.domain.useCases.BreedDetailsUseCase
import com.brunoponte.dogapp.presentation.BreedItemViewState
import com.brunoponte.dogapp.presentation.breedSearchList.BreedSearchListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel
@Inject
constructor(
    private val breedDetailsUseCase: BreedDetailsUseCase,
) : ViewModel() {

    private val _viewState = MutableLiveData<BreedDetailsViewState>()
    val viewState: LiveData<BreedDetailsViewState>
        get() = _viewState

    fun getBreedFromId(breedId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _viewState.postValue(BreedDetailsViewState.Loading)
            val response = breedDetailsUseCase.execute(breedId)
            when (response) {
                is Response.Success -> _viewState.postValue(BreedDetailsViewState
                    .Content(response.data))
                is Response.Error -> _viewState.postValue(BreedDetailsViewState
                    .Error(response.exception.message ?: ""))
            }
        }
    }

}