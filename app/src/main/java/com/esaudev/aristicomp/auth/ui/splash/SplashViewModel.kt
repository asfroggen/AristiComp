package com.esaudev.aristicomp.auth.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.aristicomp.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginRepository: AuthRepository
): ViewModel(){

    private val _loginSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccessful: LiveData<Boolean>
        get() = _loginSuccessful

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = loginRepository.login(email, password)

            _loginSuccessful.value = response.isSuccessful
        }
    }

    private val _userReadyToContinue: MutableLiveData<Boolean> = MutableLiveData()
    val userReadyToContinue: LiveData<Boolean>
        get() = _userReadyToContinue

    fun getUserData(){
        viewModelScope.launch {
            val response = loginRepository.getUserData()

            _userReadyToContinue.value = response.isSuccessful
        }
    }
}