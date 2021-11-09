package com.esaudev.aristicomp.walker.ui.my_walks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.owner.repository.walks.OwnerWalksRepository
import com.esaudev.aristicomp.utils.DataState
import com.esaudev.aristicomp.walker.repository.WalkerWalksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkerWalksViewModel @Inject constructor(
    private val walkerWalksRepository: WalkerWalksRepository
): ViewModel() {

    private val _getWalksByType: MutableLiveData<DataState<List<Walk>>> = MutableLiveData()
    val getWalksByType: LiveData<DataState<List<Walk>>>
        get() = _getWalksByType

    fun getWalksByTypeAndWalker(type: String, walkerID: String) {
        viewModelScope.launch {
            walkerWalksRepository.getWalksByTypeAndWalker(type, walkerID)
                .onEach { dataState ->
                    _getWalksByType.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}