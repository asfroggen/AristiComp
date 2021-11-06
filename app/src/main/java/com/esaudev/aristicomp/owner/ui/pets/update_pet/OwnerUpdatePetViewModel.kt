package com.esaudev.aristicomp.owner.ui.pets.update_pet

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.owner.repository.OwnerRepository
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerUpdatePetViewModel @Inject constructor(
    private val ownerRepository: OwnerRepository
): ViewModel() {

    private val _updatePetState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val updatePetState: LiveData<DataState<Boolean>>
        get() = _updatePetState

    private val _deletePetState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val deletePetState: LiveData<DataState<Boolean>>
        get() = _deletePetState

    fun updatePet(pet: Pet){
        viewModelScope.launch {
            ownerRepository.savePet(pet)
                .onEach { dataState ->
                    _updatePetState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun deletePet(pet: Pet){
        viewModelScope.launch {
            ownerRepository.deletePet(pet)
                .onEach { dataState ->
                    _deletePetState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun uploadPetImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment){
        ownerRepository.uploadPetImage(activity, imageFileURI, imageType, fragment)
    }
}