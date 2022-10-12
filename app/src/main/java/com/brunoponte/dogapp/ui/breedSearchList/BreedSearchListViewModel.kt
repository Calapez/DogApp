package com.brunoponte.dogapp.ui.breedSearchList

import androidx.lifecycle.LiveData
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
class BreedSearchListViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var query = ""

    private val _isLoading = MutableLiveData(false)
    private val _breeds = MutableLiveData(listOf<Breed>())

    val isLoading: LiveData<Boolean> = _isLoading
    val breeds: LiveData<List<Breed>> = _breeds

    fun searchBreeds(newQuery: String?) {
        query = newQuery ?: ""

        CoroutineScope(dispatcher).launch {
            _isLoading.postValue(true)
            val result = breedRepository.searchBreeds(query)
            _isLoading.postValue(false)

            _breeds.postValue(result)
        }
    }
}