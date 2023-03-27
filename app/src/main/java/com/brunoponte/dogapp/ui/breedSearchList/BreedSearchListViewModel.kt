package com.brunoponte.dogapp.ui.breedSearchList

import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import com.brunoponte.dogapp.ui.BreedItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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