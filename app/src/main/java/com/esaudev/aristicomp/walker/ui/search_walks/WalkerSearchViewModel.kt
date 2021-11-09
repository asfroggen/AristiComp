package com.esaudev.aristicomp.walker.ui.search_walks

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
class WalkerSearchViewModel @Inject constructor(
    private val walkerWalksRepository: WalkerWalksRepository
): ViewModel() {

    private val _getWalksAvailable: MutableLiveData<DataState<List<Walk>>> = MutableLiveData()
    val getWalksAvailable: LiveData<DataState<List<Walk>>>
        get() = _getWalksAvailable

    fun getWalksAvailable(){
        viewModelScope.launch {
            walkerWalksRepository.getWalksAvailable()
                .onEach { dataState ->
                    _getWalksAvailable.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    private val _logOutState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val logOutState: LiveData<DataState<Boolean>>
        get() = _logOutState

    fun logOut(){
        viewModelScope.launch {
            walkerWalksRepository.logOut()
                .onEach { dataState ->
                    _logOutState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}