package com.brunoponte.dogapp.ui.breedDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel
@Inject
constructor(
    private val breedRepository: IBreedRepository
) : ViewModel() {

    private val _selectedBreed = MutableLiveData<Breed?>(null)

    val selectedBreed: LiveData<Breed?> = _selectedBreed

    fun getBreedFromId(breedId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val breed = breedRepository.getBreed(breedId)
            _selectedBreed.postValue(breed)
        }
    }

}