package com.esaudev.aristicomp.owner.ui.walks.new_walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.owner.repository.pets.OwnerPetsRepository
import com.esaudev.aristicomp.owner.repository.walks.OwnerWalksRepository
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerNewWalkViewModel @Inject constructor(
    private val ownerWalksRepository: OwnerWalksRepository,
    private val ownerPetsRepository: OwnerPetsRepository
): ViewModel() {

    private val _saveWalkState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveWalkState: LiveData<DataState<Boolean>>
        get() = _saveWalkState

    fun saveWalk(walk: Walk){
        viewModelScope.launch {
            ownerWalksRepository.saveWalk(walk)
                .onEach { dataState ->
                    _saveWalkState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

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