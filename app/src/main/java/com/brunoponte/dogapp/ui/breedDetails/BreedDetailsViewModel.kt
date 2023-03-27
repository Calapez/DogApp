package com.brunoponte.dogapp.ui.breedDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.repository.IBreedRepository
import com.brunoponte.dogapp.ui.BreedDetailsViewState
import com.brunoponte.dogapp.ui.BreedItemViewState
import com.brunoponte.dogapp.ui.breedSearchList.BreedSearchListUseCase
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