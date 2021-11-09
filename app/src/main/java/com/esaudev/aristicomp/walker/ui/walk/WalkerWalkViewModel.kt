package com.esaudev.aristicomp.walker.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.utils.DataState
import com.esaudev.aristicomp.walker.repository.WalkerWalksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkerWalkViewModel @Inject constructor(
    private val walkerWalksRepository: WalkerWalksRepository
): ViewModel() {

    private val _acceptWalksState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val acceptWalksState: LiveData<DataState<Boolean>>
        get() = _acceptWalksState

    fun acceptWalk(walk: Walk){
        viewModelScope.launch {
            walkerWalksRepository.acceptWalk(walk)
                .onEach { dataState ->
                    _acceptWalksState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}