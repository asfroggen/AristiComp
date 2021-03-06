package com.esaudev.aristicomp.owner.ui.walks.my_walks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.owner.repository.walks.OwnerWalksRepository
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerWalksViewModel @Inject constructor(
    private val ownerWalksRepository: OwnerWalksRepository
): ViewModel() {

    private val _getWalksByType: MutableLiveData<DataState<List<Walk>>> = MutableLiveData()
    val getWalksByType: LiveData<DataState<List<Walk>>>
        get() = _getWalksByType

    fun getWalksByTypeAndOwner(type: String, ownerID: String) {
        viewModelScope.launch {
            ownerWalksRepository.getWalksBTypeAndOwner(type, ownerID)
                .onEach { dataState ->
                    _getWalksByType.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    private val _deleteWalkState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val deleteWalkState: LiveData<DataState<Boolean>>
        get() = _deleteWalkState

    fun deleteWalk(walk: Walk) {
        viewModelScope.launch {
            ownerWalksRepository.deleteWalk(walk)
                .onEach { dataState ->
                    _deleteWalkState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    private val _logOutState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val logOutState: LiveData<DataState<Boolean>>
        get() = _logOutState

    fun logOut(){
        viewModelScope.launch {
            ownerWalksRepository.logOut()
                .onEach { dataState ->
                    _logOutState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}