package com.brunoponte.dogapp.ui.breedList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val isLoading = MutableLiveData(false)
    val breeds = MutableLiveData(listOf<Breed>())
    val sortMode = MutableLiveData(SortMode.ASC)
    val listMode = MutableLiveData(ListMode.LINEAR)

    fun getFirstBreeds(force: Boolean = false) {
        // Fetches the first page of the breeds

        if (breeds.value?.isNotEmpty() == true && !force) {
            // Already got breeds
            return
        }

        CoroutineScope(dispatcher).launch {
            isLoading.postValue(true)
            val result = breedRepository.getBreeds(PAGE_SIZE, 0, sortMode.value!!)
            page += 1
            isLoading.postValue(false)

            breeds.postValue(result)
        }
    }

    fun onChangeBreedScrollPosition(position: Int) {
        scrollPosition = position

        if (reachedEndOfList() && !isLoading.value!!) {
            // Reached end of current page and isn't loading breeds. Must load next page.
            getNextPage()
        }
    }

    fun onSortClicked() {
        sortMode.value = when (sortMode.value!!) {
            SortMode.ASC -> SortMode.DESC
            SortMode.DESC -> SortMode.ASC
        }

        getFirstBreeds(force = true)
    }

    fun onListModeClicked() {
        listMode.value = when (listMode.value!!) {
            ListMode.LINEAR -> ListMode.GRID
            ListMode.GRID -> ListMode.LINEAR
        }
    }

    private fun getNextPage() {
        CoroutineScope(dispatcher).launch {
            if (reachedEndOfList()) {
                isLoading.postValue(true)

                // Prevents this to be called on first page load
                if (page > 0) {
                    val result = breedRepository.getBreeds(PAGE_SIZE, page, sortMode.value!!)

                    // Append breeds
                    val current = ArrayList(breeds.value)
                    current.addAll(result)
                    breeds.postValue(current)

                    page += 1
                }
                isLoading.postValue(false)
            }
        }
    }

    private fun reachedEndOfList() = scrollPosition >= breeds.value!!.size - 1

    companion object {
        const val PAGE_SIZE = 15
    }

}