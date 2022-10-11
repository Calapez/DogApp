package com.brunoponte.dogapp.ui.breedSearchList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedSearchListViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var query = ""

    val isLoading = MutableLiveData(false)
    val breeds = MutableLiveData(listOf<Breed>())

    fun searchBreeds(newQuery: String?) {
        query = newQuery ?: ""

        CoroutineScope(dispatcher).launch {
            isLoading.postValue(true)
            val result = breedRepository.searchBreeds(query)
            isLoading.postValue(false)

            breeds.postValue(result)
        }
    }
}