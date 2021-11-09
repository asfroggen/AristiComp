package com.esaudev.aristicomp.walker.ui.walk

import androidx.lifecycle.ViewModel
import com.esaudev.aristicomp.walker.repository.WalkerWalksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalkerWalkViewModel @Inject constructor(
    private val walkerWalksRepository: WalkerWalksRepository
): ViewModel() {


}