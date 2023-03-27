package com.brunoponte.dogapp.presentation.breedSearchList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunoponte.dogapp.domain.useCases.BreedSearchListUseCase
import com.brunoponte.dogapp.presentation.BreedItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedSearchListViewModel
@Inject
constructor(
    private val breedSearchListUseCase: BreedSearchListUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var query = ""

    private val _viewState = MutableLiveData<BreedSearchListViewState>()
    val viewState: LiveData<BreedSearchListViewState>
        get() = _viewState

    fun searchBreeds(newQuery: String?) {
        query = newQuery ?: ""

        viewModelScope.launch(dispatcher) {
            _viewState.postValue(BreedSearchListViewState.Loading)
            val result = breedSearchListUseCase.execute(query)
            _viewState.postValue(BreedSearchListViewState.Content(result.map {
                BreedItemViewState(
                    it.id,
                    it.name,
                    it.breedGroup,
                    it.referenceImageId,
                    it.origin,
                )
            }))
        }
    }
}