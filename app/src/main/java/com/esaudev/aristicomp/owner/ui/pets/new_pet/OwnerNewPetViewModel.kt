package com.esaudev.aristicomp.owner.ui.pets.new_pet

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
class OwnerNewPetViewModel @Inject constructor(
    private val ownerRepository: OwnerRepository
): ViewModel() {

    private val _savePetState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val savePetState: LiveData<DataState<Boolean>>
        get() = _savePetState

    fun savePet(pet: Pet){
        viewModelScope.launch {
            ownerRepository.savePet(pet)
                .onEach { dataState ->
                    _savePetState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun uploadPetImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment){
        ownerRepository.uploadPetImage(activity, imageFileURI, imageType, fragment)
    }
}