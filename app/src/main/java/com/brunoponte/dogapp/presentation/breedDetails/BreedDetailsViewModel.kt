package com.brunoponte.dogapp.presentation.breedDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.domain.useCases.BreedDetailsUseCase
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

    private val _selectedBreed = MutableLiveData<BreedDetailsViewState?>(null)
    val selectedBreed: LiveData<BreedDetailsViewState?>
        get() = _selectedBreed

    fun getBreedFromId(breedId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val breed = breedDetailsUseCase.execute(breedId)
            breed?.let {
                _selectedBreed.postValue(
                    BreedDetailsViewState(
                        breed.id,
                        breed.name,
                        breed.breedGroup,
                        breed.referenceImageId,
                        breed.origin,
                        breed.temperament
                    )
                )
            }
        }
    }

}