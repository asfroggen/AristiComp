package com.esaudev.aristicomp.walker.ui.finish_walk

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
class WalkerFinishViewModel @Inject constructor(
    private val walkerWalksRepository: WalkerWalksRepository
): ViewModel() {

    private val _finishWalkState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val finishWalkState: LiveData<DataState<Boolean>>
        get() = _finishWalkState

    fun finishWalk(walk: Walk){
        viewModelScope.launch {
            walkerWalksRepository.finishWalk(walk)
                .onEach { dataState ->
                    _finishWalkState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}