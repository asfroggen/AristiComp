package com.esaudev.aristicomp.owner.ui.pets.my_pets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.owner.repository.pets.OwnerPetsRepository
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPetsViewModel @Inject constructor(
    private val ownerPetsRepository: OwnerPetsRepository
): ViewModel() {

    private val _getPetsState: MutableLiveData<DataState<List<Pet>>> = MutableLiveData()
    val getPetsState: LiveData<DataState<List<Pet>>>
        get() = _getPetsState

    fun getPetsByOwner(ownerID: String){
        viewModelScope.launch {
            ownerPetsRepository.getPetsByOwner(ownerID)
                .onEach { dataState ->
                    _getPetsState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}