package com.brunoponte.dogapp.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository,
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

    private val _breeds: List<Breed>
        get() =
            if (_viewState.value is BreedListViewState.Content)
                (_viewState.value as BreedListViewState.Content).breeds
            else
                listOf()

    fun getFirstBreeds(force: Boolean = false) {
        // Fetches the first page of the breeds
        if (_breeds.isNotEmpty() && !force) {
            // Already got breeds, do nothing
            return
        }

        viewModelScope.launch(dispatcher) {
            _viewState.postValue(BreedListViewState.Loading)
            val result = breedRepository.getBreeds(PAGE_SIZE, 0, sortMode.value!!)
            page += 1
            _viewState.postValue(BreedListViewState.Content(result))
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
                _viewState.postValue(BreedListViewState.Loading)

                // Prevents this to be called on first page load
                if (page > 0) {
                    val result = breedRepository.getBreeds(PAGE_SIZE, page, sortMode.value!!)

                    // Append breeds
                    val current = ArrayList(_breeds)
                    current.addAll(result)
                    _viewState.postValue(BreedListViewState.Content(current))

                    page += 1
                }
            }
        }
    }

    private fun reachedEndOfList() = scrollPosition >= _breeds.size - 1

    companion object {
        const val PAGE_SIZE = 15
    }

}