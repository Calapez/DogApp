package com.brunoponte.dogapp.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunoponte.dogapp.ui.BreedItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel
@Inject
constructor(
    private val breedListPageUseCase: BreedListPageUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var scrollPosition = 0
    private var page = 0

    private val _sortMode = MutableLiveData(SortMode.ASC)
    private val _listMode = MutableLiveData(ListMode.LINEAR)

    val sortMode: LiveData<SortMode> = _sortMode
    val listMode: LiveData<ListMode> = _listMode

    private val _viewState = MutableLiveData<BreedListViewState>()
    val viewState: LiveData<BreedListViewState>
        get() = _viewState

    private val _viewStateBreeds: List<BreedItemViewState>
        get() = (_viewState.value as? BreedListViewState.Content)?.breeds ?: listOf()

    fun getFirstBreeds(force: Boolean = false) {
        // Fetches the first page of the breeds
        if (_viewStateBreeds.isNotEmpty() && !force) {
            // Already got breeds, do nothing
            return
        }

        viewModelScope.launch(dispatcher) {
            _viewState.postValue(BreedListViewState.Loading)
            val result = breedListPageUseCase.execute(PAGE_SIZE, 0, sortMode.value!!)
            page += 1
            _viewState.postValue(BreedListViewState.Content(result.map {
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

    fun onChangeBreedScrollPosition(position: Int) {
        scrollPosition = position

        if (reachedEndOfList() && _viewState.value !is BreedListViewState.Loading) {
            // Reached end of current page and isn't loading breeds. Must load next page.
            getNextPage()
        }
    }

    fun onSortClicked() {
        _sortMode.value = when (sortMode.value!!) {
            SortMode.ASC -> SortMode.DESC
            SortMode.DESC -> SortMode.ASC
        }

        getFirstBreeds(force = true)
    }

    fun onListModeClicked() {
        _listMode.value = when (listMode.value!!) {
            ListMode.LINEAR -> ListMode.GRID
            ListMode.GRID -> ListMode.LINEAR
        }
    }

    private fun getNextPage() {
        CoroutineScope(dispatcher).launch {
            if (reachedEndOfList()) {
                val currentBreeds = ArrayList(_viewStateBreeds)

                _viewState.postValue(BreedListViewState.Loading)

                // Prevents this to be called on first page load
                if (page > 0) {
                    val result = breedListPageUseCase.execute(PAGE_SIZE, page, sortMode.value!!)

                    // Append breeds
                    currentBreeds.addAll(result.map {
                        BreedItemViewState(
                            it.id,
                            it.name,
                            it.breedGroup,
                            it.referenceImageId,
                            it.origin,
                        )
                    })
                    _viewState.postValue(BreedListViewState.Content(currentBreeds))

                    page += 1
                }
            }
        }
    }

    private fun reachedEndOfList() = scrollPosition >= _viewStateBreeds.size - 1

    companion object {
        const val PAGE_SIZE = 15
    }

}